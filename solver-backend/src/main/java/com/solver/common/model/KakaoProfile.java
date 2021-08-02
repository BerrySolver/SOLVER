package com.solver.common.model;

import lombok.Data;

@Data
public class KakaoProfile {

	public int id;
	public String connected_at;
	public Properties properties;
	public KakaoAccount kakao_account;

	@Data
	class Properties {
		public String nickname;
	}

	@Data
	class KakaoAccount {
		public boolean profile_nickname_needs_agreement;
		public Profile profile;
		public boolean has_email;
		public boolean email_needs_agreement;
		public boolean is_email_valid;
		public boolean is_email_verified;
		public String email;

		@Data
		class Profile {
			public String nickname;
		}
	}

}
