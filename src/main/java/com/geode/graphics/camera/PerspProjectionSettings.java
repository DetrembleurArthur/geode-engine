package com.geode.graphics.camera;

public class PerspProjectionSettings
{
	private float fov;
	private float aspect;
	private float znear;
	private float zfar;

	public PerspProjectionSettings()
	{
		this(70f, 0f, 0.001f, 1000f);
	}

	public PerspProjectionSettings(float fov, float aspect, float znear, float zfar)
	{
		this.fov = fov;
		this.aspect = aspect;
		this.znear = znear;
		this.zfar = zfar;
	}

	public float getFov()
	{
		return fov;
	}

	public float getFovAsRadians()
	{
		return (float)Math.toRadians(fov);
	}

	public void setFov(float fov)
	{
		this.fov = fov;
	}

	public float getAspect()
	{
		return aspect;
	}

	public void setAspect(float aspect)
	{
		this.aspect = aspect;
	}

	public float getZnear()
	{
		return znear;
	}

	public void setZnear(float znear)
	{
		this.znear = znear;
	}

	public float getZfar()
	{
		return zfar;
	}

	public void setZfar(float zfar)
	{
		this.zfar = zfar;
	}
}