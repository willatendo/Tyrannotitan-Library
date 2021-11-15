package tyrannotitanlib.library.base.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;

public class PropertiesTracker<T extends Entity> 
{
	private int trackingTimer = 0;
	private int trackingUpdateTimer = 0;
	private boolean trackerReady = false;
	private boolean trackerDataChanged = false;

	private CompoundNBT prevTrackerData = new CompoundNBT();

	private T entity;
	private TyrannoEntityProperties properties;

	public PropertiesTracker(T entity, TyrannoEntityProperties<T> properties) {
		this.entity = entity;
		this.properties = properties;
	}

	/**
	 * Updates the tracker
	 */
	public void updateTracker() {
		int trackingFrequency = this.properties.getTrackingTime();
		if (trackingFrequency >= 0 && !this.trackerReady) {
			this.trackingTimer++;
			if (this.trackingTimer >= trackingFrequency) {
				this.trackerReady = true;
			}
		}
		int trackingUpdateFrequency = this.properties.getTrackingUpdateTime();
		if (this.trackingUpdateTimer < trackingUpdateFrequency) {
			this.trackingUpdateTimer++;
		}
		if (this.trackingUpdateTimer >= trackingUpdateFrequency) {
			if (!this.trackerDataChanged) {
				this.trackingUpdateTimer = 0;
				CompoundNBT currentTrackingData = new CompoundNBT();
				this.properties.saveTrackingSensitiveData(currentTrackingData);
				if (!currentTrackingData.equals(this.prevTrackerData)) {
					this.trackerDataChanged = true;
				}
				this.prevTrackerData = currentTrackingData;
			}
		}
	}

	/**
	 * Forces the tracker to sync the next tick
	 */
	public void setReady() {
		this.trackerReady = true;
		this.trackerDataChanged = true;
	}

	/**
	 * @return true if the data has changed and the tracking timer is ready and
	 *         resets the tracking timer
	 */
	public boolean isTrackerReady() {
		boolean ready = this.properties.getTrackingTime() >= 0 && this.trackerReady && this.trackerDataChanged;
		if (ready) {
			this.trackingTimer = 0;
			this.trackerReady = false;
			this.trackerDataChanged = false;
			return true;
		}
		return false;
	}

	/**
	 * Called when the data is syncing
	 */
	public void onSync() {
		this.properties.onSync();
	}

	/**
	 * @return the properties
	 */
	public TyrannoEntityProperties getProperties() {
		return this.properties;
	}

	/**
	 * @return the tracked entity
	 */
	public T getEntity() {
		return this.entity;
	}

	/**
	 * Removes this tracker from the property
	 */
	public void removeTracker() {
		this.properties.getTrackers().remove(this);
	}
}