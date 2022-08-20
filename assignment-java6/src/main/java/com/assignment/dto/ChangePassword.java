package com.assignment.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassword implements Serializable {

	private static final long serialVersionUID = 1604584531732644714L;

	private String currentPassword;
	private String newPassword;
}
