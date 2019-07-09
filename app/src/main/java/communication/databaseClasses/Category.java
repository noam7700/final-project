package communication.databaseClasses;

public enum Category {
	 MEAT ("���"),
	 MILK_AND_EGGS("��� ������"), 
	 BREAD("��� �����");
	 
	 String hebrewName;
	 private Category(String hebrewName) { this.hebrewName=hebrewName; }
	 
	 @Override
	 public String toString() { return hebrewName; }
}
