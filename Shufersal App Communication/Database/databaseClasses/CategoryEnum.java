package databaseClasses;

public enum CategoryEnum {
	 MEAT ("���"),
	 MILK_AND_EGGS("��� ������"), 
	 BREAD("��� �����");
	 
	 String hebrewName;
	 private CategoryEnum(String hebrewName) { this.hebrewName=hebrewName; }
	 
	 @Override
	 public String toString() { return hebrewName; }
}
