package be.designisdead.aem.demosite.core.components.models.impl.v1;

import be.designisdead.aem.demosite.core.components.models.InfoBlock;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * Created by j.peeters on 15/09/2017.
 */
@Model(adaptables = SlingHttpServletRequest.class,
    adapters = InfoBlock.class,
    defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL,
    resourceType = "apps/aem-demo-site/components/content/infoblock/v1/infoblock")
@Exporter(name = "jackson", extensions = "json")
public class InfoBlockImpl implements InfoBlock {

    @ValueMapValue
    private String message;

    @ValueMapValue
    private String viaMessage;

    // Old style, required by default
    @ValueMapValue
    private String fixedMessage;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getFixedMessage() {
        return fixedMessage;
    }

    @Override
    public void setFixedMessage(String fixedMessage) {
        this.fixedMessage = fixedMessage;
    }

    @Override
    public String getViaMessage() {
        return viaMessage;
    }

    @Override
    public void setViaMessage(String viaMessage) {
        this.viaMessage = viaMessage;
    }
}
