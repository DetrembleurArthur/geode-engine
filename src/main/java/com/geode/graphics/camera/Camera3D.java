package com.geode.graphics.camera;

import com.geode.core.KeyManager;
import com.geode.core.MouseManager;
import com.geode.core.WindowManager;
import com.geode.core.key.Keys;
import com.geode.core.mouse.ButtonState;
import org.joml.*;
import org.lwjgl.opengl.GL11;

import java.lang.Math;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

public class Camera3D extends Camera
{
	private Vector3f position = new Vector3f(0f, 0f, 1f);
	private Vector3f rotation = new Vector3f();

	private PerspProjectionSettings projectionSettings;
	private Vector2f oldMouse = new Vector2f();
	private float moveSpeed = 0.15f;
	private float mouseSensitivity = 0.15f;

	private float verticalAngle, horizontalAngle;


	public Camera3D(WindowManager windowManager)
	{
		super();
		projectionSettings = new PerspProjectionSettings();
		updateProjection();
		adaptOnResize(windowManager);
	}

	@Override
	public Matrix4f updateProjection()
	{
		projection = projection.identity().perspective(
				projectionSettings.getFovAsRadians(),
				projectionSettings.getAspect(),
				projectionSettings.getZnear(),
				projectionSettings.getZfar());
		return projection;
	}

	@Override
	public Matrix4f updateView()
	{
		view = view.identity()
				.rotateX((float)Math.toRadians(rotation.x))
				.rotateY((float)Math.toRadians(rotation.y))
				.rotateZ((float)Math.toRadians(rotation.z))
				.translate(new Vector3f(-position.x, -position.y, -position.z));
		return view;
	}

	public Vector3f getPosition()
	{
		return position;
	}


	public PerspProjectionSettings getProjectionSettings()
	{
		return projectionSettings;
	}

	public void setProjectionSettings(PerspProjectionSettings projectionSettings)
	{
		this.projectionSettings = projectionSettings;
	}

	public void update(Vector2f mousePosition)
	{
		float mouseX = mousePosition.x;
		float mouseY = mousePosition.y;
		float dx = mouseX - oldMouse.x;
		float dy = mouseY - oldMouse.y;

		rotation = rotation.add(dy * mouseSensitivity, dx * mouseSensitivity, 0);

		oldMouse = new Vector2f(mouseX, mouseY);
	}

	public void update(Vector2f mousePosition, Vector3f target)
	{
		float mouseX = mousePosition.x;
		float mouseY = mousePosition.y;
		float dx = mouseX - oldMouse.x;
		float dy = mouseY - oldMouse.y;

		verticalAngle -= dy * mouseSensitivity;
		horizontalAngle += dx * mouseSensitivity;

		float h = (float) (2.f * Math.cos(Math.toRadians(verticalAngle)));
		float v = (float) (2.f * Math.sin(Math.toRadians(verticalAngle)));

		float xo = (float) (h * Math.sin(Math.toRadians(-horizontalAngle)));
		float zo = (float) (h * Math.cos(Math.toRadians(-horizontalAngle)));

		position.set(target.x + xo, target.y - v, target.z + zo);

		rotation.set(verticalAngle, -horizontalAngle, 0f);

		oldMouse = new Vector2f(mouseX, mouseY);
	}

	public Vector3f getForward()
	{
		float cosY = (float) Math.cos(Math.toRadians(rotation.y + 90));
		float sinY = (float) Math.sin(Math.toRadians(rotation.y + 90));
		float cosP = (float) Math.cos(Math.toRadians(rotation.x));
		float sinP = (float) Math.sin(Math.toRadians(rotation.x));

		return new Vector3f(cosY * cosP, sinP, sinY * cosP);
	}

	public Vector3f getRight()
	{
		float cosY = (float) Math.cos(Math.toRadians(rotation.y));
		float sinY = (float) Math.sin(Math.toRadians(rotation.y));

		return new Vector3f(cosY, 0, sinY);
	}

	public void left()
	{
		position.add(getRight().mul(-moveSpeed));
	}

	public void right()
	{
		position.add(getRight().mul(moveSpeed));
	}

	public void toward()
	{
		position.add(getForward().mul(-moveSpeed));
	}

	public void backward()
	{
		position.add(getForward().mul(moveSpeed));
	}

	public void upaxis()
	{
		position.add(new Vector3f(0, moveSpeed, 0));
	}

	public void downaxis()
	{
		position.add(new Vector3f(0, -moveSpeed, 0));
	}

	public void towardaxis()
	{
		position.add(new Vector3f(0, 0, -moveSpeed));
	}

	public void backwardaxis()
	{
		position.add(new Vector3f(0, 0, moveSpeed));
	}

	public void rightaxis()
	{
		position.add(new Vector3f(moveSpeed, 0, 0));
	}

	public void leftaxis()
	{
		position.add(new Vector3f(-moveSpeed, 0, 0));
	}

	public void setOldMouse(Vector2f old)
	{
		oldMouse = old;
	}


	public void activateKeys()
	{
		KeyManager keyManager = KeyManager.getInstance();
		MouseManager mouseManager = MouseManager.getInstance();
		if(keyManager.isKeyPressed(Keys.S)) {
			if(mouseManager.isRightButton(ButtonState.PRESSED)) {
				leftaxis();
			} else {
				left();
			}
		}

		if(keyManager.isKeyPressed(Keys.F)) {
			if(mouseManager.isRightButton(ButtonState.PRESSED)) {
				rightaxis();
			} else {
				right();
			}
		}

		if(keyManager.isKeyPressed(Keys.E)) {
			if(mouseManager.isRightButton(ButtonState.PRESSED)) {
				towardaxis();
			} else {
				toward();
			}
		}

		if(keyManager.isKeyPressed(Keys.Z)) {
			if(mouseManager.isRightButton(ButtonState.PRESSED)) {
				backwardaxis();
			} else {
				backward();
			}
		}

		if(keyManager.isKeyPressed(Keys.D)) {
			downaxis();
		}

		if(keyManager.isKeyPressed(Keys.SPACE)) {
			upaxis();
		}
	}

	public float getMoveSpeed()
	{
		return moveSpeed;
	}

	public void setMoveSpeed(float moveSpeed)
	{
		this.moveSpeed = moveSpeed;
	}

	public float getMouseSensitivity()
	{
		return mouseSensitivity;
	}

	public void setMouseSensitivity(float mouseSensitivity)
	{
		this.mouseSensitivity = mouseSensitivity;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	@Override
	public void adaptOnResize(WindowManager windowManager) {
		projectionSettings.setAspect(windowManager.getWindow().getAspectRatio());
	}

	public void enableDepthTest() {
		GL11.glEnable(GL_DEPTH_TEST);
	}

	public void disableDepthTest() {
		GL11.glEnable(GL_DEPTH_TEST);
	}
}