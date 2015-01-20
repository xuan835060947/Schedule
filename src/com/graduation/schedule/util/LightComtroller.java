package com.graduation.schedule.util;

import android.hardware.Camera;

public class LightComtroller {
	private Camera m_Camera = null;
	boolean isLight = false;

	public LightComtroller() {
	}

	public void changeLight() {

		if (isLight) {
			closeLight();
			isLight = false;
		} else {

			openLight();
			isLight = true;
		}
	}

	public void openLight() {
		if (null == m_Camera) {
			m_Camera = Camera.open();
		}

		Camera.Parameters parameters = m_Camera.getParameters();
		parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
		m_Camera.setParameters(parameters);
		// m_Camera.autoFocus( new Camera.AutoFocusCallback (){
		// public void onAutoFocus(boolean success, Camera camera) {
		// }
		// });
		m_Camera.startPreview();
	}

	public void closeLight() {
		if (m_Camera != null) {
			m_Camera.stopPreview();
			m_Camera.release();
			m_Camera = null;
		}
	}
}
