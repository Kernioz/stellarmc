package fr.kernioz.api.playerdata.rank;

import fr.kernioz.Evania;

public enum Rank {

	FONDATEUR(15,"Fondateur", Evania.get().getConfigurationServer().getPrefix("Fondateur"), Evania.get().getConfigurationServer().getTab("Fondateur")),
	ADMIN(14,"Admin", Evania.get().getConfigurationServer().getPrefix("Admin"), Evania.get().getConfigurationServer().getTab("Admin")),
	RESPONSABLE(13,"Responsable", Evania.get().getConfigurationServer().getPrefix("Responsable"), Evania.get().getConfigurationServer().getTab("Responsable")),
	MODERATEUR(12,"Modérateur", Evania.get().getConfigurationServer().getPrefix("Modérateur"), Evania.get().getConfigurationServer().getTab("Modérateur")),
	GUIDE(11,"Guide", Evania.get().getConfigurationServer().getPrefix("Guide"), Evania.get().getConfigurationServer().getTab("Guide")),

	PARTENAIRE(10,"Partenaire", Evania.get().getConfigurationServer().getPrefix("Partenaire"), Evania.get().getConfigurationServer().getTab("Partenaire")),
	YOUTUBEUR(9,"Youtubeur", Evania.get().getConfigurationServer().getPrefix("Youtubeur"), Evania.get().getConfigurationServer().getTab("Youtubeur")),

	DEMON(8,"Demon", Evania.get().getConfigurationServer().getPrefix("Demon"), Evania.get().getConfigurationServer().getTab("Demon")),
	ANGE(7,"Ange", Evania.get().getConfigurationServer().getPrefix("Ange"), Evania.get().getConfigurationServer().getTab("Ange")),
	ROI(6,"Roi", Evania.get().getConfigurationServer().getPrefix("Roi"), Evania.get().getConfigurationServer().getTab("Roi")),
	PRINCE(5,"Prince", Evania.get().getConfigurationServer().getPrefix("Prince"), Evania.get().getConfigurationServer().getTab("Prince")),
	DUC(4,"Duc", Evania.get().getConfigurationServer().getPrefix("Duc"), Evania.get().getConfigurationServer().getTab("Duc")),
	MAGE(3,"Mage", Evania.get().getConfigurationServer().getPrefix("Mage"), Evania.get().getConfigurationServer().getTab("Mage")),
	CHEVALIER(2,"Chevalier", Evania.get().getConfigurationServer().getPrefix("Chevalier"), Evania.get().getConfigurationServer().getTab("Chevalier")),
	ECUYER(1,"Ecuyer", Evania.get().getConfigurationServer().getPrefix("Ecuyer"), Evania.get().getConfigurationServer().getTab("Ecuyer")),
	JOUEUR(0,"Paysan", "§7", "§7");

	private int id;
	private String name;
	private String tab;
	private String prefix;

	private Rank(int id,String name,String prefix, String tab) {
		this.id = id;
		this.name = name;
		this.prefix = prefix;
		this.tab = tab;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}
	public String getPrefix() {
		return prefix;
	}

	public String getTab() {
		return tab;
	}

	public static Rank get(int i) {
		for (Rank rank : values())
			if (rank.id == i) return rank;
		return null;
	}

	public static Rank get(String name) {
		for (Rank rank : values())
			if (rank.getName().equals(name)) return rank;
		return null;
	}
}
