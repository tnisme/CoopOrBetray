package game.strategies;

import game.Move;
import game.NPC;

import java.util.List;

public class RuthlessBetrayer implements NPC {

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        return Move.DEFECT;
    }

    @Override
    public String getName() {
        return "The Ruthless Betrayer";
    }

    @Override
    public String getStory() {
        return "Từ nhỏ tôi đã học được một bài học: thế giới này là kẻ mạnh ăn thịt kẻ yếu. " +
                "Tôi từng bị cha mẹ bỏ rơi, bị bạn bè phản bội. Giờ đây, tôi tin rằng " +
                "tình thương là thứ xa xỉ, và lòng tốt là dấu hiệu của sự yếu đuối. " +
                "Tôi sẽ không bao giờ tin tưởng ai, và cũng không mong ai tin tưởng tôi.";
    }

    public String getPersonality() {
        return "Tàn nhẫn, hoài nghi, và ích kỷ đến cùng cực";
    }

    public String getPhilosophy() {
        return "Tin tưởng là tự sát - phản bội là sinh tồn";
    }
}
