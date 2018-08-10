package com.pinterest.deployservice.scm;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pinterest.deployservice.bean.CommitBean;
import com.pinterest.deployservice.common.HTTPClient;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.*;

/**
 * @author evan
 * create-date 2018/8/10
 */
public class BitbucketManager extends BaseManager {

    public final static String TYPE = "Bitbucket";
    private final static String UNKNOWN_LOGIN = "UNKNOWN";
    private String apiPrefix;
    private String urlPrefix;

    private Map<String, String> headers = new HashMap<String, String>();

    public BitbucketManager(String token, String apiPrefix, String urlPrefix) {
        this.apiPrefix = apiPrefix;
        this.urlPrefix = urlPrefix;
        headers.put("Authorization", String.format("token %s", token));
    }

    @Override
    public String generateCommitLink(String repo, String sha) {
        return String.format("%s/%s/commits/%s", urlPrefix, repo, sha);
    }
    @Override
    public String getCommitLinkTemplate() {
        return String.format("%s/%%s/commits/%%s", urlPrefix);
    }

    @Override
    public String getUrlPrefix() {
        return urlPrefix;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Queue<CommitBean> getCommits(String repo, String sha, boolean keepHead) throws Exception {
        return new LinkedList<>();
    }

    @Override
    public List<CommitBean> getCommits(String repo, String startSha, String endSha, int size) throws Exception {
        return Collections.emptyList();
    }

    @Override
    public CommitBean getCommit(String repo, String sha) throws Exception {
        HTTPClient httpClient = new HTTPClient();
        String url = String.format("%s/repos/%s/commits/%s", apiPrefix, repo, sha);

        // TODO: Do not RETRY since it will timeout the thrift caller, need to revisit
        String jsonPayload = httpClient.get(url, null, null, headers, 1);
        GsonBuilder builder = new GsonBuilder();
        Map<String, Object>
                jsonMap =
                builder.create().fromJson(jsonPayload, new TypeToken<HashMap<String, Object>>() {
                }.getType());
        return toCommitBean(jsonMap, repo);
    }

    private CommitBean toCommitBean(Map<String, Object> jsonMap, String repo) {
        CommitBean CommitBean = new CommitBean();
        String sha = getSha(jsonMap);
        CommitBean.setSha(sha);
        CommitBean.setAuthor(getLogin(jsonMap));
        Map<String, Object> commitMap = (Map<String, Object>) jsonMap.get("commit");
        CommitBean.setDate(getDate(commitMap));
        String message = getMessage(commitMap);
        String[] parts = message.split("\n", 2);
        CommitBean.setTitle(parts[0]);
        if (parts.length > 1) {
            CommitBean.setMessage(parts[1]);
        }
        CommitBean.setInfo(generateCommitLink(repo, sha));
        return CommitBean;
    }

    private String getSha(Map<String, Object> jsonMap) {
        return (String) jsonMap.get("sha");
    }

    private String getLogin(Map<String, Object> jsonMap) {
        Map<String, Object> authorMap = (Map<String, Object>) jsonMap.get("author");
        if (authorMap != null) {
            return (String) authorMap.get("login");
        }
        return UNKNOWN_LOGIN;
    }
    private long getDate(Map<String, Object> jsonMap) {
        Map<String, Object> commiterMap = (Map<String, Object>) jsonMap.get("committer");
        String dateGMTStr = (String) commiterMap.get("date");
        DateTimeFormatter parser = ISODateTimeFormat.dateTimeNoMillis();
        DateTime dt = parser.parseDateTime(dateGMTStr);
        return dt.getMillis();
    }

    private String getMessage(Map<String, Object> jsonMap) {
        return (String) jsonMap.get("message");
    }

}
