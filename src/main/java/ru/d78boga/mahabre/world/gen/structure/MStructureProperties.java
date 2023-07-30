package ru.d78boga.mahabre.world.gen.structure;

public class MStructureProperties {
	public boolean underground = false;
	public int minHeight;
	public int maxHeight;
	public boolean complex = false; 
	public int blocksCount = 1;

	public MStructureProperties setHeight(int min, int max) {
		underground = true;
		minHeight = min;
		maxHeight = max;
		return this;
	}
	
	public MStructureProperties setComplex(int blocksCount) {
		complex = true;
		
		if (blocksCount < 1) {
			blocksCount = 1;
		}
		
		this.blocksCount = blocksCount;
		return this;
	}
}
