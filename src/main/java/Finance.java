import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Objects.isNull;

@Builder
@Getter
@Setter
public class Finance {
    private String date;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal adjClose;
    private BigDecimal closePrevious; // close to the previous day
    private String volume;

    // close - closePrevious
    public BigDecimal diff() {
        return this.close.subtract(this.closePrevious);
    }

    public static Finance by(Element tr, Element nextTo) {
        BigDecimal previous = isNull(nextTo) ? BigDecimal.ZERO :
                new BigDecimal(Objects.requireNonNull(nextTo).children().get(4).children().text());

        return Finance.builder()
                .date(tr.children().get(0).children().text())
                .open(new BigDecimal(tr.children().get(1).children().text()))
                .high(new BigDecimal(tr.children().get(2).children().text()))
                .low(new BigDecimal(tr.children().get(3).children().text()))
                .close(new BigDecimal(tr.children().get(4).children().text()))
                .adjClose(new BigDecimal(tr.children().get(5).children().text()))
                .volume(tr.children().get(6).children().text())
                .closePrevious(previous)
                .build();
    }

    @Override
    public String toString() {
        return "date= " + this.date + " ,open= " + this.open + " ,high= " + this.high + " ,low = "
                + this.low + " ,close= " + this.close + " ,adjClose= " + this.adjClose + " ,closePrevious= "
                + this.closePrevious + " ,diff= " + this.diff() + " ,volume= " + this.volume;
    }
}
