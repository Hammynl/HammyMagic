package com.github.hammynl.hammymagic.utils;

public enum SwitchUtil {
		BARRAGE("spell-names.barrage"),
		ESCAPE("spell-names.escape"),
		FIREBALL("spell-names.fireball"),
		LIGHTNING("spell-names.lightning"),
		TELEPORT("spell-names.teleport"),
		THUNDERBALL("spell-names.thunderball"),
		SPARK("spell-names.spark"),
		PROTECT("spell-names.protect");
		
		
		private final String code;
		
		SwitchUtil(String code) {
			this.code = code;
		}
		
		public String getName() {
			return this.code;
		}
}

