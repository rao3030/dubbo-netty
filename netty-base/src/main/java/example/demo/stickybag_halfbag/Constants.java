package example.demo.stickybag_halfbag;

public enum Constants {
    CHAT_1("心里住着一个人(3.9)，很开心。"),
    CHAT_2("不管什么样的人设，自己决定的，那就自己走，做一个形单影只的一个陌生人。或多或少，有些路，终将一人独行"),
    CHAT_3("明明置身黑暗，你却意外照射进来"),
    CHAT_4("理想太美好，现实太残酷。那么请记住，自己感动自己的时刻，go fighting。"),
    CHAT_5("路在当下，一步一脚印，一笔一世界。以后的事情，以后再说，想多了反而不好。加油"),
    CHAT_6("梦想总是要有的，不拼一下，梦想就如同白日梦一样。拼的过程，虽然心情会有点跌宕起伏，最后的那一步，永远是最好的。"),
    CHAT_7("当光明无法守护你，我只能投身黑暗重披战甲，哪怕化作恶魔陷入无尽的深渊，也要护你一生一世。"),
    CHAT_8("自己选择的路，不要去幻想结局是什么，想的再好不过也是一片浮云。好的结局是靠自己争。");
    private final String src;

    private Constants(String src) {
        this.src = src;
    }

    public String getSrc() {
        return src;
    }
}
