package ru.d78boga.mahabre;

import java.util.Random;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;

public class mMath {
	private static Random rand = new Random();
	
	public static float distance(Vec2f vec2) {
		float x = vec2.x;
		float y = vec2.y;
		return MathHelper.sqrt(x * x + y * y);
	}
	
	public static int clamp(int value, int min, int max) {
		if (min >= max) new IllegalArgumentException();
		
		value = min <= value ? value : min;
		value = value <= max ? value : max;
		return value;
	}
	
	public static boolean roll(int antichance) {
		return rand.nextInt(antichance) == 0;
	}
}
