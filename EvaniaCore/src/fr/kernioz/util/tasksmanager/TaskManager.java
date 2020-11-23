package fr.kernioz.util.tasksmanager;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import fr.kernioz.Evania;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class TaskManager {

	private static HashMap<String, Integer>	taskList	= new HashMap<String, Integer>();
	public static BukkitScheduler			scheduler	= Bukkit.getScheduler();
	static Plugin							plugin		= Evania.get();

	public TaskManager() {

	}

	/**
	 * Recupère la task
	 * @param taskName
	 * @return
	 */
	public BukkitTask getTask(String taskName) {
		BukkitTask task = null;
		int id = getTaskId(taskName);
		if (id > 0) {
			for (BukkitTask pendingTask : scheduler.getPendingTasks()) {
				if (pendingTask.getTaskId() == id) return task;
			}
		}
		return null;
	}

	/**
	 * Tente de récupérer le nom de la task par l'id si elle existe encore et qu'elle a été déclaré dans ce manager
	 * @param id de la task
	 * @return null si non trouvé
	 */
	public static String getTaskNameById(int id) {
		for (Entry<String, Integer> entry : taskList.entrySet()) {
			if (entry.getValue() == id) return entry.getKey();
		}
		return null;
	}

	// La tâche existe ?
	public static boolean taskExist(String taskName) {
		if (taskList.containsKey(taskName)) {
			return true;
		}
		return false;
	}

	// Récupération de l'id
	public static int getTaskId(String taskName) {
		if (taskExist(taskName)) {
			return taskList.get(taskName);
		}
		return 0;
	}

	// Cancel all task
	public static void cancelAllTask() {
		for (int taskId : taskList.values()) {
			scheduler.cancelTask(taskId);
		}
	}

	// Cancel de la task by name
	public static boolean cancelTaskByName(String taskName) {
		if (taskExist(taskName)) {
			int taskId = getTaskId(taskName);
			taskList.remove(taskName);
			scheduler.cancelTask(taskId);
			return true;
		}
		return false;
	}

	// Annule une tâche par l'ID
	public static void cancelTaskById(int id) {
		scheduler.cancelTask(id);
	}

	public static void removeTaskByName(String taskName) {
		taskList.remove(taskName);
	}

	/**
	 * Useless autant appelé directement TaskManager.cancelTaskByName(taskName) qui en plus retourne le status de
	 * l'annulation
	 * @deprecated
	 */
	public static void checkIfExist(String taskName) {
		if (TaskManager.taskExist(taskName)) TaskManager.cancelTaskByName(taskName);
	}

	// Run task now
	public static BukkitTask runTask(Runnable runnable) {
		return scheduler.runTask(plugin, runnable);
	}

	// Run task later
	public static BukkitTask runTaskLater(Runnable runnable, int tick) {
		return scheduler.runTaskLater(plugin, runnable, tick);
	}

	/**
	 * Créer et enregistre une task, se retire de la liste toute seule à l'expiration, permet de l'annuler dans un
	 * plugin et éviter les mémory leaks
	 * @param taskName
	 * @param task
	 * @param duration
	 */
	public static BukkitTask runTaskLater(final String taskName, Runnable task, int duration) {
		BukkitTask bukkitTask = scheduler.runTaskLater(plugin, task, duration);
		final int id = bukkitTask.getTaskId();
		TaskManager.addTask(taskName, id);
		runTaskLater(new Runnable() {
			@Override
			public void run() {
				// Toujours la même task ID pour éviter la suppression de task renouvelées
				if (taskList.get(taskName) != null && taskList.get(taskName) == id) taskList.remove(taskName);
			}
		}, duration);
		return bukkitTask;
	}

	/**
	 * Rajout une task dans la list
	 * @param name
	 * @param id
	 */
	public static void addTask(String name, int id) {
		taskList.put(name, id);
	}

	/**
	 * Ajoute une tâche répétitive Annule la précédante du meme nom si existe.
	 * @param runnable
	 * @param delay
	 * @param refresh
	 * @return
	 */
	public static BukkitTask scheduleSyncRepeatingTask(String taskName, Runnable runnable, int delay, int refresh) {
		cancelTaskByName(taskName);
		BukkitTask task = scheduler.runTaskTimer(plugin, runnable, delay, refresh);
		taskList.put(taskName, task.getTaskId());
		return task;
	}

	/**
	 * Créer un nom de tâche unique basé sur un nom de tâche
	 * @param string
	 * @return
	 */
	public static String getTaskName(String string) {
		String taskName = string + "_" + new Random().nextInt(99999);
		while (taskExist(taskName)) {
			taskName = string + "_" + new Random().nextInt(99999);
		}
		return taskName;
	}

}
