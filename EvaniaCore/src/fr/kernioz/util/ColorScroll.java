package fr.kernioz.util;

import org.bukkit.ChatColor;

public class ColorScroll
{
	private int position;
	private String str;
	private String colorBefore;
	private String colorAfter;
	private String colorMid;
	private boolean bold;
	private ChatColor textColor;
	private boolean upperCaseMid;
	int waitTime;

	public ColorScroll(ChatColor textColor, String str, String colorMid, String colorBefore, String colorAfter, boolean bold, boolean upperCaseMid, int waitTime) {
		this.str = str;
		this.colorMid = colorMid;
		this.position = -1;
		this.bold = bold;
		this.colorBefore = colorBefore;
		this.colorAfter = colorAfter;
		this.textColor = textColor;
		this.upperCaseMid = upperCaseMid;
		this.waitTime = waitTime;
	}

	public String next() {

		if (position > str.length()+waitTime) {
			position = 0;
		}


		if(position >= str.length()) {
			String one = str.substring(position - 1, str.length() - 1);

			if(bold) {
				String two = upperCaseMid == true ? colorMid + ChatColor.BOLD + one.toUpperCase() : colorMid + ChatColor.BOLD + one;
				String fin = textColor + "" + ChatColor.BOLD + str.substring(0, str.length() - 1) +
						colorBefore + ChatColor.BOLD +
						str.substring(str.length() - 1, str.length()) + two;
				position = -1;
				return fin;
			} else {
				String two = upperCaseMid == true ? colorMid + one.toUpperCase() : colorMid + one;
				String fin = textColor + str.substring(0, str.length() - 1) +
						colorBefore + str.substring(str.length() - 1, str.length()) + two;
				position = -1;
				return fin;
			}
		}

		if(position <= -1) {
			position++;
			if(bold) {
				return colorBefore + ChatColor.BOLD + str.substring(0, 1) + textColor + ChatColor.BOLD + str.substring(1, str.length());
			} else {
				return colorBefore + str.substring(0, 1) + textColor + str.substring(1, str.length());  
			}
		}

		if(position == 0) {
			String one = str.substring(0, 1);

			if(bold) {
				String two = upperCaseMid == true ? colorMid + ChatColor.BOLD + one.toUpperCase() : colorMid + ChatColor.BOLD + one;
				String fin = two + colorAfter + ChatColor.BOLD +
						str.substring(1, 2) + textColor + ChatColor.BOLD +
						str.substring(2, str.length());
				position++;
				return fin;
			} else {
				String two = upperCaseMid == true ? colorMid + one.toUpperCase() : colorMid + one;
				String fin = two + colorAfter +
						str.substring(1, 2) + textColor +
						str.substring(2, str.length());
				position++;
				return fin;
			}
		}

		if(position >= 1) {
			String one = str.substring(0, position);
			String two = str.substring(position + 1, str.length());

			if(bold) {
				String three = upperCaseMid == true ? colorMid + ChatColor.BOLD + str.substring(position, position + 1).toUpperCase() : colorMid + ChatColor.BOLD + str.substring(position, position + 1);
				String fin = null;

				int m = one.length();
				int l = two.length();

				String first = m <= 1 ? colorBefore + ChatColor.BOLD + one : ChatColor.BOLD + one.substring(0, one.length() - 1) + colorBefore +
						ChatColor.BOLD + one.substring(one.length() - 1, one.length());

				String second = l <= 1 ? colorAfter + ChatColor.BOLD + two :
					colorAfter + ChatColor.BOLD + two.substring(0, 1) +
					textColor + ChatColor.BOLD + two.substring(1, two.length());

				fin = textColor + first + three + second;

				position++;
				return fin;

			} else {
				String three = upperCaseMid == true ? colorMid + str.substring(position, position + 1).toUpperCase() : colorMid + str.substring(position, position + 1);

				String fin = null;

				int m = one.length();
				int l = two.length();

				String first = m <= 1 ? colorBefore + one : one.substring(0, one.length() - 1) + colorBefore +
						one.substring(one.length() - 1, one.length());

				String second = l <= 1 ? colorAfter + two :
					colorAfter + two.substring(0, 1) +
					textColor + two.substring(1, two.length());

				fin = textColor + first + three + second;

				position++;
				return fin;
			}
		}

		return null;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int stringLength() {
		return str.length();
	}

	public ChatColor getTextColor() {
		return textColor;
	}

	public void setTextColor(ChatColor textColor) {
		this.textColor = textColor;
	}

	public boolean isUpperCaseMid() {
		return upperCaseMid;
	}

	public void setUpperCaseMid(boolean upperCaseMid) {
		this.upperCaseMid = upperCaseMid;
	}

}
