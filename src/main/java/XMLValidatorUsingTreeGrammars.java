

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.StringReader;
import java.util.Arrays;

// * author: Ronald Haentjens Dekker
// * date: 11-09-2018
public class XMLValidatorUsingTreeGrammars {

    public void parse(String XML_input) throws XMLStreamException {
        // hier maken we een stax parser aan die de XML in stukjes binnen laat komen

        XMLInputFactory factory = XMLInputFactory.newInstance();
        System.out.println("FACTORY: " + factory);

        XMLEventReader reader = factory.createXMLEventReader(new StringReader(XML_input));

        // nu moet ik een state machine creeren, die van de ene naaar de andere state gaat
        // door middel van transitierules
        StateMachine stateMachine = new StateMachine();
        createTransitionRules();

        // we gaan de input af, event voor event.


        while (reader.hasNext()) {
            XMLEvent xmlEvent = reader.nextEvent();
            System.out.println(xmlEvent);
            // nu checken we of we van state naar state kunnen gaan.
            if (xmlEvent.isStartElement()) {
                StartElement s = xmlEvent.asStartElement();
                String tag = s.getName().getLocalPart();
                stateMachine.processInput(tag);
            }
        }



    }

    private void createTransitionRules() {
        // We maken de non terminal root aan. (hoofdletters)
        // Die kunen we vervagnen door ene terminal root (kleine letters) + non terminal MARKUP node
        // Dit klinkt ingewikkelder dan nodig. hmm
        // de huidige state s dan meer ene tree, waarbij steeds een stukje vervangen wordt.
        // Tree zou je eigenlijk een kunnen aanmaken op basis van een string, maar ja nu even niet.
        Tree lhs1 = new Tree("ROOT");
        Tree rhs2 =  new Tree("root", Arrays.asList("MARKUP"));
        TransitionRule transitionRule1 =  new TransitionRule(lhs1, rhs2);
    }
}
