package org.jboss.reddeer.snippet.test.example;

import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;

public class LoginShell {
	
	public static final String SHELL_TITLE = "Credentials";

	public LoginShell() {
		new DefaultShell(SHELL_TITLE);
	}

	public void setUsername(String username) {
		Text usernameText = new DefaultText("Username:");
		usernameText.setText(username);
	}

	public void setPassword(String password) {
		Text passwordText = new DefaultText("Password:");
		passwordText.setText(password);
	}

	public void login() {
		Button loginButton = new PushButton("Login");
		loginButton.click();
		new WaitWhile(new ShellWithTextIsActive(SHELL_TITLE));
	}
}