Interface.class: Interface.java
	javac Interface.java

build: Interface.class

run: build
	java -cp .:mssql-jdbc-11.2.0.jre11.jar Interface

clean:
	rm Interface.class
	rm CrashDatabase.class