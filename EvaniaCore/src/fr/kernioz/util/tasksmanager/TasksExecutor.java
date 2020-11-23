package fr.kernioz.util.tasksmanager;

import java.util.concurrent.LinkedBlockingQueue;

public class TasksExecutor implements Runnable {

	private LinkedBlockingQueue<PendingTask> pending = new LinkedBlockingQueue<PendingTask>();

	public void addTask(PendingTask message) {
		pending.add(message);
	}

	@Override
	public void run() {
		while (true) {
			try {
				pending.take().run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
