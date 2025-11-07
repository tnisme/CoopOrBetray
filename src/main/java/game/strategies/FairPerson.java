package game.strategies;

import game.Move;
import game.NPC;

import java.util.List;

public class FairPerson implements NPC {
    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        if (opponentHistory.isEmpty()) {
            return Move.COOPERATE;
        } else {
            return opponentHistory.get(opponentHistory.size() - 1);
        }
    }

    @Override
    public String getName() {
        return "Fair Person";
    }

    public String getStory() {
        return "Là một thẩm phán về hưu, tôi tin vào công lý và sự công bằng. " +
                "Tôi luôn cho mọi người cơ hội đầu tiên, nhưng cũng sẵn sàng trả đũa " +
                "nếu bị đối xử không công bằng. Đây không phải là trả thù, mà là duy trì " +
                "sự cân bằng trong mọi mối quan hệ. Mắt đổi mắt, răng đổi răng - đó là công lý cơ bản nhất";
    }

    public String getPersonality() {
        return "Công bằng, nguyên tắc, và nhất quán";
    }
}
