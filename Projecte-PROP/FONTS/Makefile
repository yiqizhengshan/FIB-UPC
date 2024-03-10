all: PackageExceptions PackageDomain PackagePresentation PackagePersistence 

PackageExceptions:
	javac -cp .:main/java main/java/exceptions/*.java

PackagePersistence:
	javac -cp .:main/java main/java/Persistence/*.java

PackageDomain:
	javac -cp .:main/java main/java/Domain/*.java

PackagePresentation:
	javac -cp .:main/java main/java/Presentation/*.java

exe: 
	java -cp .:main/java Presentation.Main

clean:
	rm -f main/java/Presentation/*.class main/java/Persistence/*.class main/java/Domain/*.class main/java/exceptions/*.class
