package org.chathamrobotics.nova.system;

import android.support.annotation.IdRes;
import android.view.View;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.CameraCalibration;

import org.chathamrobotics.nova.robot.Robot;
import org.chathamrobotics.nova.robot.RobotConfiguration;
import org.chathamrobotics.nova.util.RobotLogger;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.internal.vuforia.VuforiaLocalizerImpl;

import java.util.concurrent.BlockingQueue;

/**
 * A management system for vuforia
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class VuforiaManager extends RobotSystemImpl implements VuforiaLocalizer {
    private final static String TAG = VuforiaManager.class.getSimpleName();
    private final static String CAMERA_MONITOR_VIEW_ID = "cameraMonitorViewId";

    /**
     * A Pausable Vuforia
     */
    public static class PausableVuforia extends VuforiaLocalizerImpl {
        private boolean isPaused;

        private final Runnable pauseGlSurface = new Runnable() {
            @Override
            public void run() {
                if (glSurface != null) {
                    glSurface.onPause();
                    glSurface.setVisibility(View.INVISIBLE);
                }
            }
        };

        private final Runnable resumeGlSurface = new Runnable() {
            @Override
            public void run() {
                if (glSurface != null) {
                    glSurface.onResume();
                    glSurface.setVisibility(View.VISIBLE);
                }
            }
        };

        /**
         * Creates a new instance of {@link PausableVuforia}
         * @param parameters    the vuforia parameters
         */
        public PausableVuforia(VuforiaLocalizer.Parameters parameters) {
            super(parameters);
        }

        /**
         * Pauses vuforia
         */
        public void pause() {
            if (isPaused) return;

            pauseAR();
            activity.runOnUiThread(pauseGlSurface);

            isPaused = true;
        }

        /**
         * Resumes vuforia after pausing
         */
        public void resume() {
            if (! isPaused) return;

            resumeAR();
            activity.runOnUiThread(resumeGlSurface);

            isPaused = false;
        }

        /**
         * Closes vuforia
         */
        public void close() {
            super.close();
        }
    }

    /**
     * The configuration required to build a {@link VuforiaManager}
     */
    public interface Configuration extends RobotConfiguration {
        String getVuforiaLicenseKey();

        CameraDirection getVuforiaCameraDirection();

        boolean showCameraMonitor();
    }

    /**
     * Builds a new instance of {@link VuforiaManager}
     * @param robot         the robot
     * @param configuration the configuration fot the system
     * @return              the built instance of {@link VuforiaManager}
     */
    public static VuforiaManager build(Robot robot, Configuration configuration) {
        HardwareMap hardwareMap = robot.getHardwareMap();

        VuforiaLocalizer.Parameters parameters;

        if (configuration.showCameraMonitor()) {
            @IdRes int cameraMonitorView = hardwareMap.appContext.getResources().getIdentifier(
                    CAMERA_MONITOR_VIEW_ID,
                    "id",
                    hardwareMap.appContext.getPackageName()
            );

            parameters = new VuforiaLocalizer.Parameters(cameraMonitorView);
        } else parameters = new VuforiaLocalizer.Parameters();


        return new VuforiaManager(parameters, robot.logger.child(TAG));
    }

    private final VuforiaLocalizer.Parameters parameters;
    private final Thread vuforiaInitializer = new Thread(new Runnable() {
        @Override
        public void run() {
            logger.debug.log("Initializing Vuforia");

            vuforia = new PausableVuforia(parameters);

            setState(State.INITIALIZED);
        }
    }, "VuforiaInitializer");

    private PausableVuforia vuforia;

    /**
     * Creates a new instance of {@link VuforiaManager}
     * @param parameters    the parameters for vuforia
     * @param logger        the robot logger
     */
    public VuforiaManager(VuforiaLocalizer.Parameters parameters, RobotLogger logger) {
        super(logger);

        this.parameters = parameters;
    }

    // ACCESSORS

    /**
     * @see VuforiaLocalizer#getCameraCalibration()
     */
    @Override
    public CameraCalibration getCameraCalibration() {
        confirmInitialized("getCameraCalibration");

        return vuforia.getCameraCalibration();
    }

    /**
     * @see VuforiaLocalizer#getFrameQueue()
     */
    @Override
    public BlockingQueue<CloseableFrame> getFrameQueue() {
        confirmInitialized("getFrameQueue");

        return vuforia.getFrameQueue();
    }

    /**
     * @see VuforiaLocalizer#setFrameQueueCapacity(int)
     */
    @Override
    public void setFrameQueueCapacity(int capacity) {
        confirmInitialized("setFrameQueueCapacity");
    }

    /**
     * @see VuforiaLocalizer#getFrameQueueCapacity()
     */
    @Override
    public int getFrameQueueCapacity() {
        confirmInitialized("getFrameQueueCapacity");

        return vuforia.getFrameQueueCapacity();
    }

    /**
     * Gets the {@link VuforiaLocalizer} used internally
     * @return  the {@link VuforiaLocalizer} used internally
     */
    public PausableVuforia getVuforia() {
        return vuforia;
    }

    // BEHAVIOR

    /**
     * Initializes the {@link VuforiaManager}
     */
    @Override
    public void init() {
        logger.info.log("Initializing");

        logger.debug.log("Starting vuforia initialization thread");

        vuforiaInitializer.start();
    }

    /**
     * Starts the {@link VuforiaManager}
     */
    @Override
    public void start() {
        preStart();

        logger.info.log("Starting");

        setState(State.RUNNING);
    }

    /**
     * Stops the {@link VuforiaManager}
     */
    @Override
    public void stop() {
        logger.info.log("Stopping");

        removeOpenListeners();

        if (vuforiaInitializer.isAlive()) {
            vuforiaInitializer.interrupt();
        }

        if (vuforia != null) {
            vuforia.close();
            vuforia = null;
        }

        setState(State.STOPPED);
    }

    /**
     * Pauses Vuforia. This will not unlock the camera, but will save resources
     */
    public void pause() {
        confirmRunning("pause");

        vuforia.pause();
    }

    /**
     * Resumes Vuforia after pausing
     */
    public void resume() {
        confirmRunning("resume");

        vuforia.resume();
    }

    /**
     * @see VuforiaLocalizer#loadTrackablesFromAsset(String)
     */
    @Override
    public VuforiaTrackables loadTrackablesFromAsset(String assetName) {
        confirmInitialized("loadTrackablesFromAsset");

        return vuforia.loadTrackablesFromAsset(assetName);
    }

    /**
     * @see VuforiaLocalizer#loadTrackablesFromFile(String)
     */
    @Override
    public VuforiaTrackables loadTrackablesFromFile(String absoluteFileName) {
        confirmInitialized("loadTrackablesFormFile");

        return vuforia.loadTrackablesFromFile(absoluteFileName);
    }
}
