
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;


public class WScraping {
    public static void main(String[] args) {
        try {
            Document dc = getDocument("https://finance.yahoo.com/quote/AAPL/history");

            Element body = dc.getElementsByTag("tbody").first();

            Integer cantValid = 7;

            List<Finance> finances = new ArrayList<>();

            List<Discard> discards = new ArrayList<>();

            List<Element> elementos = body.getElementsByTag("tr");

            for (Element tr : elementos) {

                Element nextElement = tr.nextElementSibling();

                if (isNull(nextElement)) {
                    Finance finance = Finance.by(tr, null);
                    finances.add(finance);
                    break;
                }

                if (!cantValid.equals(tr.childNodeSize())) {
                    Discard discard = Discard.by(tr);
                    discards.add(discard);
                    continue;
                }

                if (!cantValid.equals(nextElement.childNodeSize())) {
                    Discard discard = Discard.by(tr);
                    discards.add(discard);
                    nextElement = nextElement.nextElementSibling();
                }

                Finance finance = Finance.by(tr, nextElement);
                finances.add(finance);

            }

            finances.forEach(System.out::println);
            discards.forEach(System.out::println);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Document getDocument(String url) {
        Connection conn = Jsoup.connect(url);
        Document document = null;
        try {
            document = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}
