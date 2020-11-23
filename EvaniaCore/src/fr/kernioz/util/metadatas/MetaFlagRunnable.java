package fr.kernioz.util.metadatas;

import fr.kernioz.util.tasksmanager.TaskManager;
import org.bukkit.metadata.Metadatable;

public class MetaFlagRunnable implements Runnable {

	protected String		taskName;
	protected String		flag;
	protected Metadatable	metadatable;

	public MetaFlagRunnable(Metadatable metadatable, String taskName, String flag) {
		this.taskName = taskName;
		this.flag = flag;
		this.metadatable = metadatable;
	}

	@Override
	public void run() {
		Flags.removeFlag(metadatable, flag);
		TaskManager.removeTaskByName(taskName);
	}

}
