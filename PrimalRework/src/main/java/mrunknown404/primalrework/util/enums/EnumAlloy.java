package mrunknown404.primalrework.util.enums;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mrunknown404.unknownlibs.utils.DoubleValue;
import mrunknown404.unknownlibs.utils.MathUtils;

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
				
				float total = 0;
				for (DoubleValue<EnumAlloy, Integer> dv : metals) {
					total += dv.getR();
				}
				
				for (AlloyRequirement req : reqs) {
					for (DoubleValue<EnumAlloy, Integer> dv : metals) {
						if (req.alloy == dv.getL() && req.within(MathUtils.floor(((float) dv.getR() / total) * 100))) {
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
	
	public static EnumAlloy getAlloy(Map<EnumAlloy, Integer> metalsIn) {
		List<DoubleValue<EnumAlloy, Integer>> metalsOut = new ArrayList<DoubleValue<EnumAlloy, Integer>>();
		
		Iterator<Entry<EnumAlloy, Integer>> it = metalsIn.entrySet().iterator();
		while (it.hasNext()) {
			Entry<EnumAlloy, Integer> pair = it.next();
			metalsOut.add(new DoubleValue<EnumAlloy, Integer>(pair.getKey(), pair.getValue()));
		}
		
		return getAlloy(metalsOut);
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
