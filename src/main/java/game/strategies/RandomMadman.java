package game.strategies;

import game.Move;
import game.NPC;

import java.util.List;

public class RandomMadman implements NPC {

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        Move[] moves = Move.values();
        int index = (int) (Math.random() * moves.length);
        return moves[index];
    }

    @Override
    public String getName() {
        return "Random Madman";
    }

    public String getStory() {
        return "Tôi từng là một nhà khoa học nghiên cứu về sự ngẫu nhiên, cho đến một ngày " +
                "tôi phát hiện ra rằng mọi thứ trong vũ trụ này đều là may rủi. " +
                "Giờ đây, tôi sống theo triết lý: không có quyết định nào là đúng hay sai, " +
                "chỉ có những kết quả ngẫu nhiên. Tôi là hiện thân của sự hỗn loạn.";
    }

    public String getPersonality() {
        return "Không thể đoán trước, điên rồ, và triết lý";
    }

}
