package game.strategies;

import game.Move;
import game.NPC;

import java.util.List;

public class GrudgeHolder implements NPC {

    private boolean trust = true;

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        if (opponentHistory.isEmpty()) return Move.COOPERATE;
        if (opponentHistory.get(opponentHistory.size() - 1) == Move.DEFECT && trust) trust = false;
        return trust ? Move.COOPERATE : Move.DEFECT;
    }

    @Override
    public String getName() {
        return "Grudge Holder";
    }

    @Override
    public String getStory() {
        return "Cả gia đình tôi đã chết vì sự phản bội của người bạn thân nhất. " +
                "Từ đó, tôi thề: một khi lòng tin bị phá vỡ, nó sẽ không bao giờ được hàn gắn. " +
                "Tôi cho mọi người một cơ hội, nhưng chỉ MỘT lần duy nhất. " +
                "Phản bội tôi một lần, và bạn sẽ trở thành kẻ thù của tôi mãi mãi.";
    }

    @Override
    public String getPersonality() {
        return "Cứng nhắc, không khoan nhượng, và cực kỳ thù dai";
    }
}
