-injars       target\HW_01.1-maven.jar
-outjars      target\HW_01.1-maven_out.jar
-libraryjars  <java.home>/lib/rt.jar
-printmapping HW_01.1-maven.map
-dontwarn

-keep public class ru.otus.hw011.Main {
      public static void main(java.lang.String[]);
}