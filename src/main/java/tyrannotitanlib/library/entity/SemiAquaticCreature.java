package tyrannotitanlib.library.entity;

public interface SemiAquaticCreature {
	boolean shouldEnterWater();

	boolean shouldLeaveWater();

	boolean shouldStopMoving();

	int getWaterSearchRange();
}
