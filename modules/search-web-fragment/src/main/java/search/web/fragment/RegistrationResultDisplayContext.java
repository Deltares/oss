package search.web.fragment;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import nl.deltares.portal.model.impl.AbsDsdArticle;
import nl.deltares.portal.model.impl.SessionRegistration;

import java.text.SimpleDateFormat;

public class RegistrationResultDisplayContext {

  public RegistrationResultDisplayContext(long classPK, ThemeDisplay themeDisplay) {
    this._classPK = classPK;
    this._themeDisplay = themeDisplay;

    _registrationArticle = JournalArticleLocalServiceUtil.fetchLatestArticle(this._classPK);

    try {
      AbsDsdArticle parentInstance = AbsDsdArticle.getInstance(_registrationArticle);
      if (parentInstance instanceof SessionRegistration) {
        _sessionRegistration = (SessionRegistration) parentInstance;
      }
    } catch (Exception e) {
      LOG.debug("Error getting Registration instance [" + _registrationArticle.getArticleId() + "]", e);
    }
  }

  public String getSmallImageURL() {
    String url = "";
    if (getSession() != null) {
      url = getSession().getSmallImageURL(_themeDisplay);
    }
    return url;
  }

  public String getPresenterSmallImageURL() {
    String url = "";
    if (getSession() != null && getSession().getPresenter() != null) {
      url = getSession().getPresenter().getSmallImageURL(_themeDisplay);
    }
    return url;
  }

  public String getPresenterName() {
    String name = "";
    if (getSession().getPresenter() != null) {
      name = getSession().getPresenter().getTitle();
    }
    return name;
  }

  private SessionRegistration getSession() {
    return _sessionRegistration;
  }

  public String getStartTime() {
    String time = "";
    if (getSession() != null) {
      time = timeFormat.format(getSession().getStartTime());
    }
    return time;
  }

  public String getEndTime() {
    String time = "";
    if (getSession() != null) {
      time = timeFormat.format(getSession().getEndTime());
    }
    return time;
  }

  private final long _classPK;
  private final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
  private final ThemeDisplay _themeDisplay;
  private final JournalArticle _registrationArticle;
  private SessionRegistration _sessionRegistration;

  private static final Log LOG = LogFactoryUtil.getLog(RegistrationResultDisplayContext.class);
}
