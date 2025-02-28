/**package com.example.RoomSpacePlanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlgorithmUtils {

    // Add all the algorithm functions here
    public static List<float[]> ransacPlaneDetection(List<float[]> points, int maxIterations, float threshold) {
        List<float[]> bestInliers = new ArrayList<>();
        for (int i = 0; i < maxIterations; i++) {
            Collections.shuffle(points);
            float[] p1 = points.get(0);
            float[] p2 = points.get(1);
            float[] p3 = points.get(2);

            float[] normal = crossProduct(subtract(p2, p1), subtract(p3, p1));
            float d = -dotProduct(normal, p1);

            List<float[]> inliers = new ArrayList<>();
            for (float[] point : points) {
                float distance = Math.abs(dotProduct(normal, point) + d) / magnitude(normal);
                if (distance < threshold) {
                    inliers.add(point);
                }
            }

            if (inliers.size() > bestInliers.size()) {
                bestInliers = inliers;
            }
        }
        return bestInliers;
    }

    // Include other algorithms (raycasting, gesture recognition, z-buffering, etc.)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            for (HitResult hit : arFrame.hitTest(event.getX(), event.getY())) {
                Trackable trackable = hit.getTrackable();
                if (trackable instanceof Plane && ((Plane) trackable).isPoseInPolygon(hit.getHitPose())) {
                    // Place the 3D object at the hit position
                    Anchor anchor = hit.createAnchor();
                    placeFurniture(anchor);
                    break;
                }
            }
        }
        return super.onTouchEvent(event);
    }



    private void placeFurniture(Anchor anchor) {
        // Attach the 3D model to the anchor
        Node furnitureNode = new Node();
        furnitureNode.setParent(arFragment.getArSceneView().getScene());
        furnitureNode.setRenderable(furnitureRenderable);
        furnitureNode.setWorldPosition(anchor.getPose().extractTranslation());
    }

    //gesture
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            // Pinch Gesture
            float currentDistance = getTouchDistance(event);
            if (initialDistance > 0) {
                float scale = initialScale * (currentDistance / initialDistance);
                furnitureNode.setScale(new Vector3(scale, scale, scale));
            }
            initialDistance = currentDistance;
        } else if (event.getPointerCount() == 1) {
            // Drag Gesture
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                float deltaX = event.getX() - previousX;
                furnitureNode.setRotation(new Quaternion(Vector3.up(), deltaX * ROTATION_FACTOR));
            }
            previousX = event.getX();
        }
        return true;
    }

    private float getTouchDistance(MotionEvent event) {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }



    //depth
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glEnable(GL10.GL_DEPTH_TEST); // Enable depth testing
        gl.glClearDepthf(1.0f);          // Set Z-buffer clear value
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); // Clear buffers
        renderScene(gl);
    }




    // Helper methods for RANSAC
    private static float[] crossProduct(float[] a, float[] b) {
        return new float[]{a[1] * b[2] - a[2] * b[1], a[2] * b[0] - a[0] * b[2], a[0] * b[1] - a[1] * b[0]};
    }

    private static float dotProduct(float[] a, float[] b) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }

    private static float magnitude(float[] a) {
        return (float) Math.sqrt(dotProduct(a, a));
    }

    private static float[] subtract(float[] a, float[] b) {
        return new float[]{a[0] - b[0], a[1] - b[1], a[2] - b[2]};
    }
}
 */
