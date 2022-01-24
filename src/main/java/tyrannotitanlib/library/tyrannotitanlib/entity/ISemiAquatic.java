package tyrannotitanlib.library.tyrannotitanlib.entity;

public interface ISemiAquatic {
	boolean shouldEnterWater();

	boolean shouldLeaveWater();

	boolean shouldStopMoving();

	int getWaterSearchRange();
}
