package tw.slmt.collada.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import tw.slmt.collada.parse.ColladaData;
import tw.slmt.collada.parse.Parser;
import tw.slmt.collada.parse.geo.mesh.MeshData;

public class SimpleMeshViewer {

	private static final String WINDOW_TITLE = "Collada Parser - Simple Mesh Viewer";

	private long glfwWindowHandle;
	private int verticesBufferId, indicesBufferId;
	private int numOfIndices;

	public static void main(String[] args) throws IOException {
		// TODO: It should be more general.
		Parser parser = new Parser();
		ColladaData data = parser.parseToColladaData(new FileInputStream(new File("examples/teapot.dae")));
		MeshData mesh = (MeshData) data.getGeomertires().get(0);
		mesh.normalize();
		
		SimpleMeshViewer viewer = new SimpleMeshViewer(mesh);
		viewer.loop();
		viewer.terminate();
	}

	public SimpleMeshViewer(MeshData mesh) {
		initGlfw();
		initOpengl(mesh);
	}
	
	public void loop() {
		while (GLFW.glfwWindowShouldClose(glfwWindowHandle) != GLFW.GLFW_TRUE) {
			IntBuffer widthBuf = MemoryUtil.memAllocInt(1);
			IntBuffer heightBuf = MemoryUtil.memAllocInt(1);

			GLFW.glfwGetFramebufferSize(glfwWindowHandle, widthBuf, heightBuf);

			renderScene();

			GLFW.glfwSwapBuffers(glfwWindowHandle);
			GLFW.glfwPollEvents();
		}
	}
	
	public void terminate() {
		GLFW.glfwDestroyWindow(glfwWindowHandle);
		GLFW.glfwTerminate();
	}

	private void initGlfw() {
		GLFW.glfwSetErrorCallback(GLFWErrorCallback.createPrint());
		if (GLFW.glfwInit() != GLFW.GLFW_TRUE)
			System.exit(-1);

		glfwWindowHandle = GLFW.glfwCreateWindow(1024, 768, WINDOW_TITLE,
				MemoryUtil.NULL, MemoryUtil.NULL);
		if (glfwWindowHandle == MemoryUtil.NULL) {
			GLFW.glfwTerminate();
			System.exit(-1);
		}

		GLFW.glfwMakeContextCurrent(glfwWindowHandle);
		GLFW.glfwSwapInterval(1);
	}

	private void initOpengl(MeshData mesh) {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		//
		// - https://www.lwjgl.org/guide
		GL.createCapabilities();

		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		createVertexBuffer(mesh.getVertexData());
		createIndexBuffer(mesh.getVertexIndices());
		numOfIndices = mesh.getVertexIndices().length;
	}
	
	private void createVertexBuffer(float[] vertices) {
		FloatBuffer vertBuffer = BufferUtils.createFloatBuffer(vertices.length);
		vertBuffer.put(vertices);
		vertBuffer.rewind();

		verticesBufferId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesBufferId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertBuffer, GL15.GL_STATIC_DRAW);
	}
	
	private void createIndexBuffer(int[] indices) {
		IntBuffer indBuffer = BufferUtils.createIntBuffer(indices.length);
		indBuffer.put(indices);
		indBuffer.rewind();
		
		indicesBufferId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indBuffer, GL15.GL_STATIC_DRAW);
	}
	
	private void renderScene() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		GL20.glEnableVertexAttribArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesBufferId);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);

		GL11.glDrawElements(GL11.GL_TRIANGLES, numOfIndices, GL11.GL_UNSIGNED_INT, 0);

		GL20.glDisableVertexAttribArray(0);
	}
}
