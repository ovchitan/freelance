import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Scanner;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class HelloWorld {

    final String TITLE = "game";
    int count = 0;
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private long window;

    public void run() throws FileNotFoundException {
        Solid body = new Solid(pointReader("c.obj"));
        try {
            try {
                body.resize();
                init(body);
                loop(body);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            glfwTerminate();
            errorCallback.release();
        }
    }

    private ArrayList<Triangle> triangulation;
    private Vector3f axis = new Vector3f();
    private Vector3f movement = new Vector3f();

    private void init(Solid body) throws FileNotFoundException {
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
        if (glfwInit() != GL11.GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_TRUE); // the window will not stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int WIDTH = GLFWvidmode.width(vidmode);
        int HEIGHT = GLFWvidmode.height(vidmode);

        triangulation = body.triangulate();

        window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, glfwGetPrimaryMonitor(), NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                    glfwSetWindowShouldClose(window, GL_TRUE);
                } else {
                    if (key == GLFW_KEY_W && action == GLFW_PRESS) {
                        movement.add(new Vector3f((float) 0.1, 0, 0));
                    } else {
                        if (key == GLFW_KEY_S && action == GLFW_PRESS) {
                            movement.add(new Vector3f((float) -0.1, 0, 0));
                        } else {
                            if (key == GLFW_KEY_A && action == GLFW_PRESS) {
                                movement.add(new Vector3f(0, (float) 0.1, 0));
                            } else {
                                if (key == GLFW_KEY_D && action == GLFW_PRESS) {
                                    movement.add(new Vector3f(0, (float) -0.1, 0));
                                } else {
                                    if (key == GLFW_KEY_Q && action == GLFW_PRESS) {
                                        movement.add(new Vector3f(0, 0, (float) 0.1));
                                    } else {
                                        if (key == GLFW_KEY_E && action == GLFW_PRESS) {
                                            movement.add(new Vector3f(0, 0, (float) -0.1));
                                        } else {
                                            if (key == GLFW_KEY_T && action == GLFW_PRESS) {
                                                axis.add(new Vector3f((float) 0.1, 0, 0));
                                            } else {
                                                if (key == GLFW_KEY_G && action == GLFW_PRESS) {
                                                    axis.add(new Vector3f((float) -0.1, 0, 0));
                                                } else {
                                                    if (key == GLFW_KEY_R && action == GLFW_PRESS) {
                                                        axis.add(new Vector3f(0, 0, (float) 0.1));
                                                    } else {
                                                        if (key == GLFW_KEY_Y && action == GLFW_PRESS) {
                                                            axis.add(new Vector3f(0, 0, (float) -0.1));
                                                        } else {
                                                            if (key == GLFW_KEY_F && action == GLFW_PRESS) {
                                                                axis.add(new Vector3f(0, (float) 0.1, 0));
                                                            } else {
                                                                if (key == GLFW_KEY_H && action == GLFW_PRESS) {
                                                                    axis.add(new Vector3f(0, (float) -0.1, 0));
                                                                } else {
                                                                    if (key == GLFW_KEY_EQUAL && action == GLFW_PRESS) {
                                                                        body.scale((float) 0.8);
                                                                        //ind ++;
                                                                    } else {
                                                                        if (key == GLFW_KEY_MINUS && action == GLFW_PRESS) {
                                                                            //ind --;
                                                                            body.scale((float) 1.2);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
        movement.add(new Vector3f(-0.7, 0, 0));
        body.scale((float) 0.01);
    }

    private void loop(Solid q) throws FileNotFoundException {
        GLContext.createFromCurrent();
        Mountain tmp = new Mountain(0);
        Mountain tmp2 = new Mountain((float) 0.5);
        ArrayList<Triangle> mounts = tmp.triangulate(new Vector3f(1,0,0), 0);
        mounts.addAll(tmp2.triangulate(new Vector3f(1,1,0), (float)0.1));
        triangulation = q.triangulate();
        glClearColor(0.0f, 1.0f, 0.0f, 0.0f);
        DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        while (glfwWindowShouldClose(window) == GL_FALSE) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            count++;

            glfwGetCursorPos(window, b1, b2);

            float x = (float) (b1.get(0) / GLFWvidmode.width(vidmode));
            float y = (float) b2.get(0) / GLFWvidmode.height(vidmode);

            //glViewport(-30, -30, width, height);
            glClear(GL_COLOR_BUFFER_BIT);
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(-1, 1, -1.f, 1.f, 1.f, -1.f);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();
            glEnable(GL_DEPTH_TEST);
            glMatrixMode(GL_MODELVIEW);


            //glPushMatrix();
            //glRotatef((float) count,0,1,0);
            //glRotatef(ry[0],0,1,0);
            //glRotatef(rz[0],0,0,1);
            //glTranslatef(x[0],y[0],z[0]);

            //drawTriangle(mounts.get(ind));
            //glPopMatrix();

            glPushMatrix();
            glRotatef(count * axis.get()[0], 1, 0, 0);
            glRotatef(count * axis.get()[1], 0, 1, 0);
            glRotatef(count * axis.get()[2], 0, 0, 1);
            glTranslatef(movement.get()[0], movement.get()[1], movement.get()[2]);
            //triangulation.forEach(this::drawTriangle);
            mounts.forEach(this::drawTriangle);
            //movement = new Vector3f();
            //axis = new Vector3f();
            glPopMatrix();
            sync(60);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    final static String PATH = "./objects/";

    private ArrayList<Obj> pointReader(String filename) throws FileNotFoundException {
        Scanner in = new Scanner(new File(PATH + filename));
        ArrayList<Vector3f> res = new ArrayList<Vector3f>();
        ArrayList<Vector3f> cons = new ArrayList<Vector3f>();
        String colour = "";
        String file = "";
        ArrayList<Obj> objects = new ArrayList<>();
        int count = -1;
        int num = 0;
        int currNum = 0;
        while (in.hasNextLine()) {
            String curr = in.nextLine();
            if (curr.length() > 1) {
                if (curr.charAt(0) == 'v' && curr.charAt(1) == ' ') {
                    String[] split = curr.split(" ");
                    float[] tmp = new float[3];
                    for (int i = 1; i < 4; i++) {
                        tmp[i - 1] = Float.parseFloat(split[i]);
                    }
                    currNum++;
                    res.add(new Vector3f(tmp));
                } else {
                    if (curr.charAt(0) == 'f') {
                        String[] split = curr.split(" ");

                        float[] tmp = new float[3];
                        for (int i = 1; i < 4; i++) {
                            tmp[i - 1] = Float.parseFloat(split[i]) - num;
                        }
                        cons.add(new Vector3f(tmp));
                    } else {
                        if (curr.charAt(0) == 'm') {
                            String[] split = curr.split(" ");
                            file = split[1];
                        } else {
                            if (curr.charAt(0) == 'o') {
                                if (count != -1) {
                                    objects.get(count).set(res, cons, colour);
                                }
                                count++;
                                cons.clear();
                                res.clear();
                                num = currNum;
                                objects.add(new Obj());
                            }
                            if (curr.charAt(0) == 'u') {
                                String[] split = curr.split(" ");
                                colour = split[1];
                            }
                        }
                    }
                }
            }
        }
        objects.get(count).set(res, cons, colour);
        in.close();
        in = new Scanner(new File(PATH + file));
        String a = "";
        while (in.hasNextLine()) {
            String curr = in.nextLine();
            if (curr.length() > 1) {
                if (curr.charAt(0) == 'n') {
                    if (curr.charAt(1) == 'e') {
                        String[] split = curr.split(" ");
                        a = split[1];
                    }
                } else {
                    if (curr.charAt(0) == 'K' && curr.charAt(1) == 'd') {
                        String[] cols = curr.split(" ");
                        for (Obj t : objects) {
                            if (t.getCol().equals(a)) {
                                t.setCol(new Vector3f(Float.parseFloat(cols[1]), Float.parseFloat(cols[2]),
                                        Float.parseFloat(cols[3])));
                            }
                        }
                    }
                }
            }
        }
        in.close();
        return objects;
    }

    public void drawTriangle(Triangle t) {
        glBegin(GL_TRIANGLES);
        glColor3f(t.get()[3].get()[0], t.get()[3].get()[1], t.get()[3].get()[2]);
        glVertex3f(t.get()[0].get()[0], t.get()[0].get()[1], t.get()[0].get()[2]);
        //glColor3f(0.f, 1.f, 0.f);
        glVertex3f(t.get()[1].get()[0], t.get()[1].get()[1], t.get()[1].get()[2]);
        //glColor3f(0.f, 0.f, 1.f);
        glVertex3f(t.get()[2].get()[0], t.get()[2].get()[1], t.get()[2].get()[2]);
        glEnd();
    }

    private long variableYieldTime, lastTime;

    private void sync(int fps) {
        if (fps <= 0) return;

        long sleepTime = 1000000000 / fps; // nanoseconds to sleep this frame
        long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000 * 1000));
        long overSleep = 0; // time the sync goes over by

        try {
            while (true) {
                long t = System.nanoTime() - lastTime;

                if (t < sleepTime - yieldTime) {
                    Thread.sleep(1);
                } else if (t < sleepTime) {
                    // burn the last few CPU cycles to ensure accuracy
                    Thread.yield();
                } else {
                    overSleep = t - sleepTime;
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);

            // auto tune the time sync should yield
            if (overSleep > variableYieldTime) {
                // increase by 200 microseconds (1/5 a ms)
                variableYieldTime = Math.min(variableYieldTime + 200 * 1000, sleepTime);
            } else if (overSleep < variableYieldTime - 200 * 1000) {
                // decrease by 2 microseconds
                variableYieldTime = Math.max(variableYieldTime - 2 * 1000, 0);
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        new HelloWorld().run();
    }

}