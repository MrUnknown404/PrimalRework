package mrunknown404.primalrework.util.enums;

import java.util.ArrayList;
import java.util.List;

import mrunknown404.primalrework.util.DoubleValue;

/**<pre>
 * unknown
 * copper
 * tin
 * bronze
 * </pre> */
public enum EnumAlloy {
	unknown,
	copper,
	tin,
	bronze;
	
	public static EnumAlloy getAlloy(List<DoubleValue<EnumAlloy, Integer>> metals) {
		for (EnumAlloy alloy : EnumAlloy.values()) {
			if (alloy != unknown) {
				List<AlloyRequirement> reqs = alloy.getAlloyRequirements();
				int matched = 0;
				
				for (AlloyRequirement req : reqs) {
					for (DoubleValue<EnumAlloy, Integer> dv : metals) {
						if (req.alloy == dv.getL() && req.within(dv.getR())) {
							matched++;
						}
					}
				}
				
				if (matched == reqs.size()) {
					return alloy;
				}
			}
		}
		
		return unknown;
	}
	
	public List<AlloyRequirement> getAlloyRequirements() {
		List<AlloyRequirement> list = new ArrayList<AlloyRequirement>();
		
		switch (this) {
			case copper:
				list.add(new AlloyRequirement(EnumAlloy.copper, 95, 100));
				break;
			case tin:
				list.add(new AlloyRequirement(EnumAlloy.tin, 95, 100));
				break;
			case bronze:
				list.add(new AlloyRequirement(EnumAlloy.copper, 85, 90));
				list.add(new AlloyRequirement(EnumAlloy.tin, 10, 15));
				break;
			case unknown: break;
		}
		
		return list;
	}
	
	public static class AlloyRequirement {
		public final EnumAlloy alloy;
		public final int min, max;
		
		public AlloyRequirement(EnumAlloy alloy, int min, int max) {
			this.alloy = alloy;
			this.min = min;
			this.max = max;
		}
		
		public boolean within(int n) {
			return n >= min && n <= max ? true : false;
		}
		
		@Override
		public String toString() {
			return "(" + alloy.toString() + " " + min + "% - " + max + "%)";
		}
	}
}
