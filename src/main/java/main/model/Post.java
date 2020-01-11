package main.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    private Date time;
    private String title;
    private String text;

    @Column(name = "view_count")
    private Integer viewCount;

    @ElementCollection(targetClass = ModerationStatus.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<ModerationStatus> moderationStatuses;

    @Column(name = "is_active")
    private Byte active;

    @Column(name = "moderator_id")
    private Integer moderatorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Set<ModerationStatus> getModerationStatuses() {
        return moderationStatuses;
    }

    public void setModerationStatuses(Set<ModerationStatus> moderationStatuses) {
        this.moderationStatuses = moderationStatuses;
    }

    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    public Integer getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(Integer moderatorId) {
        this.moderatorId = moderatorId;
    }
}
