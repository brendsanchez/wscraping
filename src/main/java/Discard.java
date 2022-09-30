import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class Discard {
    private String date;
    private BigDecimal dividend;

    public static Discard by(Element tr) {
        BigDecimal resultValid = new BigDecimal(tr.children().get(1).children()
                .text().replace(" Dividend", ""));

        return Discard.builder()
                .date(tr.children().get(0).children().text())
                .dividend(resultValid)
                .build();
    }

    @Override
    public String toString() {
        return "date= " + this.date + " ,dividend= " + this.dividend;
    }
}
