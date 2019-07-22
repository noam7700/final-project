package databaseClasses;

public enum CategoryEnum {
	 MEAT ("בשר"),
	 MILK_AND_EGGS("חלב וביצים"), 
	 BREAD("לחם ומאפה");
	 
	 String hebrewName;
	 private CategoryEnum(String hebrewName) { this.hebrewName=hebrewName; }
	 
	 @Override
	 public String toString() { return hebrewName; }
}
