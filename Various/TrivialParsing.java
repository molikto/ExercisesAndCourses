import java.util.ArrayList;
import java.util.HashMap;

public class TrivialParsing {


    String test = "Fred,Karl,Technician,2010–Karl,Cathy,VP,2009–Cathy,NULL,CEO,2007\nAdam,Karl,Technician,2010–Bob,Karl,Technician,2012–Cathy,Karl,Technician,2013–Karl,Nancy,Manager,2009–Wendy,Nancy,Technician,2012–Nancy,NULL,CEO,2007\n";

    int index = 0;

    public boolean not(char c) {
        return index < test.length() && test.charAt(index) != c;
    }

    public boolean not(char c, char b) {
        return index < test.length() && test.charAt(index) != c && test.charAt(index) != b;
    }

    public void skip(char c) {
        if (index < test.length() && test.charAt(index) == c) index += 1;
    }

    public String parseUntil(char c) {
        int start = index;
        while (not(c)) index += 1;
        return test.substring(start, index);
    }

    public String parseUntil(char c, char b) {
        int start = index;
        while (not(c, b)) index += 1;
        return test.substring(start, index);
    }

    int caseNo = 1;

    public void parseCase() {
        System.out.println("Case #"  + (caseNo++));
        HashMap<String, Person> ps = new HashMap<String, Person>();
        Person ceo = null;
        while (not('\n')) {
            Person p =  parsePerson();
            if (p.boss.equals("NULL")) ceo = p;
            ps.put(p.name, p);
        }
        for (Person p : ps.values()) {
            if (p != ceo) {
                ps.get(p.boss).ps.add(p);
            }
        }
        ceo.print(0);
        skip('\n');
    }


    private Person parsePerson() {
        String name = parseUntil(',');
        skip(',');
        String boss = parseUntil(',');
        skip(',');
        String job = parseUntil(',');
        skip(',');
        String year = parseUntil('–', '\n');
        skip('–');
        return new Person(name, boss, job, year);
    }

    public void parse() {
        while (index < test.length()) {
            parseCase();
        }
    }

    static class Person {
        private final String year;
        private final String job;
        private final String name;
        private final String boss;

        public Person(String name, String boss, String job, String year) {
            this.name = name;
            this.boss = boss;
            this.job = job;
            this.year = year;
        }

        public ArrayList<Person> ps = new ArrayList<Person>();

        public void print(int l) {
            for (int i = 0; i < l && i < 1; i++) System.out.print('-');
            System.out.println(name + " (" + job + ") " + year);
            for (Person p : ps) p.print(l + 1);
        }
    }

    public static void main(String[] args) {
        new TrivialParsing().parse();
    }
}