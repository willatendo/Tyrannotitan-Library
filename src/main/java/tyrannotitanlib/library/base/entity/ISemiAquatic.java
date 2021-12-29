package tyrannotitanlib.library.base.entity;

public interface ISemiAquatic {
	boolean shouldEnterWater();

	boolean shouldLeaveWater();

	boolean shouldStopMoving();

	int getWaterSearchRange();
}
