import java.util.stream.Collectors;
import java.util.*;
import java.io.*;

public class Main {

  public static class Coord {

    public int r;

    public int c;

    public Coord(int r, int c) {
      this.r = r;
      this.c = c;
    }

    public Coord add(Coord other) {
      return new Coord(this.r + other.r, this.c + other.c);
    }
  }

  public static class Robot {
    private Coord coord;

    private boolean destroyed = false;

    public Robot(Coord c) {
      this.coord = c;
    }

    public void move(Coord d) {
      coord = coord.add(d);
    }

    public boolean isDestroyed() {
      return destroyed;
    }

    public Coord getCoord() {
      return coord;
    }

    public void setDestroyed(boolean value) {
      this.destroyed = value;
    }
  }

  public static class Game {

    List<Robot> robots;

    Coord player;

    int playerCommands[];

    char [][] board;

    int [][] robotCount;

    int R;

    int C;

    Coord[] dirs = {
            new Coord(1, -1),
            new Coord(1, 0),
            new Coord(1, 1),
            new Coord(0, -1),
            new Coord(0, 0),
            new Coord(0, 1),
            new Coord(-1, -1),
            new Coord(-1, 0),
            new Coord(-1, 1)
    };

    public Game(String[] boardString, String commands) {
      this.R = boardString.length;
      this.C = boardString[0].length();
      initBoard(boardString);
      initCommand(commands);
    }

    private void initRobotCount() {
      for(int i = 0 ; i < R ; i++) {
        for(int j = 0 ; j < C ; j++) {
          robotCount[i][j] = 0;
        }
      }
    }

    private int calcDist(Coord x, Coord y) {
      return Math.abs(x.r - y.r) + Math.abs(x.c - y.c);
    }

    public void print() {
      for(int i = 0 ; i < R ; i++) {
        for(int j = 0 ; j < C ; j++) {
          System.out.print(board[i][j]);
        }
        System.out.print("\n");
      }
    }

    public int play() {
      // 아래와 같은 순서를 반복한다.
      //  1. 플레이어 커맨드를 가져와서 플레이어를 옮긴다.
      //  2. 각 로봇들의 위치에서 플레이어에게 가는 최단거리 움직임을 구한다.
      //  3. 각 로봇들이 가려는 위치를 옮긴 위치의 로봇 Position, robotCount 를 1 증가시킨다.
      //  4. 로봇 이동과 플레이어의 위치가 변경되고 나면
      //  5. 플레이어와 로봇이 겹치는지 우선 확인한다.
      //  6. 겹친다면 게임을 종료한다.
      //  7. 겹치지 않았다면 로봇끼리 충돌 여부를 확인하고
      //  8. 파괴되지 않은 로봇만 추려낸다.
      //  9. 로봇 위치 카운트를 초기화한다.
      int answer = 0;

      int cmdIndex = 0;

      while (cmdIndex < playerCommands.length) {
//        System.out.println("################# round " + cmdIndex + " ##############################");
//        print();
//        System.out.println("---------------------------------------------");
        if(isRobotPlaced(player) ) {
          answer = cmdIndex;
          break;
        }
        int cmd = playerCommands[cmdIndex++];
        Coord nextDir = dirs[cmd];
        Coord nxt = player.add(nextDir);

        if (board[nxt.r][nxt.c] == 'R') {
          answer = cmdIndex;
          break;
        }

        board[player.r][player.c] = '.';
        board[nxt.r][nxt.c] = 'I';
        player.r = nxt.r;
        player.c = nxt.c;

        for (Robot robot : robots) {
          Coord nxtDir = dirs[4];
          int minDist = Integer.MAX_VALUE;

          Coord robotCur = robot.getCoord();
          for (int i = 0; i < dirs.length; i++) {
            if (i == 4) continue;

            Coord robotNxt = robotCur.add(dirs[i]);

            if (!isInRange(robotNxt)) {
              continue;
            }

            int dist = calcDist(player, robotNxt);
            if (dist > minDist) {
              continue;
            }

            minDist = dist;
            nxtDir = dirs[i];
          }
          robot.move(nxtDir);
          Coord rCoord = robot.getCoord();
          robotCount[rCoord.r][rCoord.c]++;
        }

        if(robotCount[player.r][player.c] > 0 ){
          answer = cmdIndex;
          break;
        }

        updateRobotStatus();
        boardUpdate();
      }

      return answer;
    }

    private boolean isRobotPlaced(Coord crd) {
      return board[crd.r][crd.c] == 'R';
    }

    private void boardUpdate() {
      for(int i = 0 ; i < R ; i++) {
        for(int j = 0 ; j < C ; j++) {
          board[i][j] = '.';
          robotCount[i][j]=0;
        }
      }

      board[player.r][player.c] = 'I';

      robots.forEach(it -> {
        Coord crd = it.getCoord();
        board[crd.r][crd.c] = 'R';
      });
    }

    private void updateRobotStatus() {

      for(int i = 0 ; i < robots.size() ; i++) {
        Robot robot = robots.get(i);
        int r = robot.getCoord().r;
        int c = robot.getCoord().c;

        if(robotCount[r][c] > 1) {
          robot.setDestroyed(true);
          //board[r][c] = '.';
        }
      }

      robots = robots.stream()
              .filter(it -> !it.destroyed)
              .collect(Collectors.toList());
    }

    private boolean isInRange(Coord crd) {
      return (crd.r >= 0 && crd.r < R) && (crd.c >=0 && crd.c < C);
    }

    private void initCommand(String commands) {
      playerCommands = new int[commands.length()];

      for(int i = 0 ; i < commands.length() ; i++) {
        playerCommands[i] = Integer.parseInt(commands.substring(i, i+1));
        playerCommands[i]--;
      }
    }

    private void initBoard(String[] boardString) {
      //0 is empty space
      // 1 is player
      // 2 is robot
      board = new char[R][C];
      robots = new ArrayList<>();
      robotCount = new int[R][C];

      for(int r = 0 ; r < R ; r++) {
        for(int c = 0 ; c < C ; c++) {
          char ch = boardString[r].charAt(c);
          board[r][c] = ch;

          if(ch == 'R') {
            robots.add(new Robot(new Coord(r, c)));
          }else if(ch == 'I') {
            player = new Coord(r, c);
          }
        }
      }
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int answer = 0;
    int R, C;

    String[] sizeString = br.readLine().split(" ");
    R = Integer.parseInt(sizeString[0]);
    C = Integer.parseInt(sizeString[1]);
    String[] board = new String[R];

    for(int i = 0 ; i < R ; i++) {
      board[i] = br.readLine();
    }

    String command = br.readLine();

    Game game = new Game(board, command);
    int result = game.play();

    if(result == 0) {
      game.print();
    } else {
      System.out.println("kraj " + result);
    }
    br.close();
  }
}
