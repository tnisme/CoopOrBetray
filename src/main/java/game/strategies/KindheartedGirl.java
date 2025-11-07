package game.strategies;

import game.Move;
import game.NPC;

import java.util.List;

public class KindheartedGirl implements NPC {

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        return Move.COOPERATE;
    }

    @Override
    public String getName() {
        return "Kindhearted Girl";
    }

    @Override
    public String getStory() {
        return "Tôi lớn lên trong một ngôi làng nhỏ nơi mọi người luôn giúp đỡ lẫn nhau. " +
                "Cha mẹ dạy tôi rằng lòng tốt sẽ luôn được đền đáp. Dù có bị tổn thương, " +
                "tôi vẫn giữ vững niềm tin vào bản chất tốt đẹp của con người. " +
                "Một ngày nào đó, sự chân thành của tôi sẽ chạm đến trái tim kẻ xấu nhất.";
    }

    @Override
    public String getPersonality() {
        return "Ngây thơ, tốt bụng, và không bao giờ từ bỏ niềm tin";
    }
}
