class Solution {

    private int N;

    private List<Command> commands;

    static class Command {
        String str;
        int ts;

        Command(String str, int ts) {
            this.str = str;
            this.ts = ts;
        }
    }

    private String getString(int from) {
        for(int i = commands.size() - 1 ; i >= 0 ; --i) {
            Command cmd = commands.get(i);
            if(cmd.ts < from) {
                return cmd.str;
            }
        }

        return "";
    }

    public Solution(BufferedReader reader) throws IOException {

        N = Integer.parseInt(reader.readLine());
        commands = new ArrayList<>();
        commands.add(new Command("", 0));

        for(int i = 0 ; i < N ; i++) {
            String[] tokens = reader.readLine().split(" ");
            String cmd = tokens[0];

            if(cmd.equals("type")) {
                commands.add(new Command(commands.get(commands.size() - 1).str + tokens[1], Integer.parseInt(tokens[2])));
            } else {
                int end = Integer.parseInt(tokens[2]);
                int start = end - Integer.parseInt(tokens[1]);
                String str = getString(start);
                commands.add(new Command(str, end));
            }
        }
    }

    public void run() {
        System.out.println(commands.get(commands.size() - 1).str);
    }
}
