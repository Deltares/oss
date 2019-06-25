package nl.worth.deltares.oss.subversion.tasks.impl;


import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.StorageTypeAware;
import com.liferay.portal.kernel.scheduler.Trigger;


public class StorageTypeAwareSchedulerEntryImpl extends SchedulerEntryImpl implements SchedulerEntry, StorageTypeAware {

  private SchedulerEntryImpl _schedulerEntry;
  private StorageType _storageType;

  public StorageTypeAwareSchedulerEntryImpl(final SchedulerEntryImpl schedulerEntry, final StorageType storageType) {
    super();

    _schedulerEntry = schedulerEntry;
    _storageType = storageType;
  }

  public StorageTypeAwareSchedulerEntryImpl(final SchedulerEntryImpl schedulerEntry) {

    new StorageTypeAwareSchedulerEntryImpl(schedulerEntry, StorageType.MEMORY_CLUSTERED);
  }

  public String getDescription() {
    return _schedulerEntry.getDescription();
  }

  public String getEventListenerClass() {
    return _schedulerEntry.getEventListenerClass();
  }

  public StorageType getStorageType() {
    return _storageType;
  }

  public Trigger getTrigger() {
    return _schedulerEntry.getTrigger();
  }

  public void setDescription(final String description) {
    _schedulerEntry.setDescription(description);
  }

  public void setTrigger(final Trigger trigger) {
    _schedulerEntry.setTrigger(trigger);
  }

  public void setEventListenerClass(final String eventListenerClass) {
    _schedulerEntry.setEventListenerClass(eventListenerClass);
  }
}
