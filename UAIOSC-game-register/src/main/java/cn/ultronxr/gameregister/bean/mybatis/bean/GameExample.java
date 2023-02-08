package cn.ultronxr.gameregister.bean.mybatis.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class GameExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GameExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andGameIdIsNull() {
            addCriterion("game_id is null");
            return (Criteria) this;
        }

        public Criteria andGameIdIsNotNull() {
            addCriterion("game_id is not null");
            return (Criteria) this;
        }

        public Criteria andGameIdEqualTo(Long value) {
            addCriterion("game_id =", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdNotEqualTo(Long value) {
            addCriterion("game_id <>", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdGreaterThan(Long value) {
            addCriterion("game_id >", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdGreaterThanOrEqualTo(Long value) {
            addCriterion("game_id >=", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdLessThan(Long value) {
            addCriterion("game_id <", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdLessThanOrEqualTo(Long value) {
            addCriterion("game_id <=", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdIn(List<Long> values) {
            addCriterion("game_id in", values, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdNotIn(List<Long> values) {
            addCriterion("game_id not in", values, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdBetween(Long value1, Long value2) {
            addCriterion("game_id between", value1, value2, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdNotBetween(Long value1, Long value2) {
            addCriterion("game_id not between", value1, value2, "gameId");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(Long value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(Long value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(Long value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(Long value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(Long value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<Long> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<Long> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(Long value1, Long value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(Long value1, Long value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNull() {
            addCriterion("account_id is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNotNull() {
            addCriterion("account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(Integer value) {
            addCriterion("account_id =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotEqualTo(Integer value) {
            addCriterion("account_id <>", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThan(Integer value) {
            addCriterion("account_id >", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("account_id >=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThan(Integer value) {
            addCriterion("account_id <", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("account_id <=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIn(List<Integer> values) {
            addCriterion("account_id in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotIn(List<Integer> values) {
            addCriterion("account_id not in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("account_id between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("account_id not between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNull() {
            addCriterion("app_id is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("app_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(Integer value) {
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(Integer value) {
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(Integer value) {
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(Integer value) {
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(Integer value) {
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<Integer> values) {
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<Integer> values) {
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(Integer value1, Integer value2) {
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(Integer value1, Integer value2) {
            addCriterion("app_id not between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andShopIsNull() {
            addCriterion("shop is null");
            return (Criteria) this;
        }

        public Criteria andShopIsNotNull() {
            addCriterion("shop is not null");
            return (Criteria) this;
        }

        public Criteria andShopEqualTo(String value) {
            addCriterion("shop =", value, "shop");
            return (Criteria) this;
        }

        public Criteria andShopNotEqualTo(String value) {
            addCriterion("shop <>", value, "shop");
            return (Criteria) this;
        }

        public Criteria andShopGreaterThan(String value) {
            addCriterion("shop >", value, "shop");
            return (Criteria) this;
        }

        public Criteria andShopGreaterThanOrEqualTo(String value) {
            addCriterion("shop >=", value, "shop");
            return (Criteria) this;
        }

        public Criteria andShopLessThan(String value) {
            addCriterion("shop <", value, "shop");
            return (Criteria) this;
        }

        public Criteria andShopLessThanOrEqualTo(String value) {
            addCriterion("shop <=", value, "shop");
            return (Criteria) this;
        }

        public Criteria andShopLike(String value) {
            addCriterion("shop like", value, "shop");
            return (Criteria) this;
        }

        public Criteria andShopNotLike(String value) {
            addCriterion("shop not like", value, "shop");
            return (Criteria) this;
        }

        public Criteria andShopIn(List<String> values) {
            addCriterion("shop in", values, "shop");
            return (Criteria) this;
        }

        public Criteria andShopNotIn(List<String> values) {
            addCriterion("shop not in", values, "shop");
            return (Criteria) this;
        }

        public Criteria andShopBetween(String value1, String value2) {
            addCriterion("shop between", value1, value2, "shop");
            return (Criteria) this;
        }

        public Criteria andShopNotBetween(String value1, String value2) {
            addCriterion("shop not between", value1, value2, "shop");
            return (Criteria) this;
        }

        public Criteria andActualPlayShopIsNull() {
            addCriterion("actual_play_shop is null");
            return (Criteria) this;
        }

        public Criteria andActualPlayShopIsNotNull() {
            addCriterion("actual_play_shop is not null");
            return (Criteria) this;
        }

        public Criteria andActualPlayShopEqualTo(String value) {
            addCriterion("actual_play_shop =", value, "actualPlayShop");
            return (Criteria) this;
        }

        public Criteria andActualPlayShopNotEqualTo(String value) {
            addCriterion("actual_play_shop <>", value, "actualPlayShop");
            return (Criteria) this;
        }

        public Criteria andActualPlayShopGreaterThan(String value) {
            addCriterion("actual_play_shop >", value, "actualPlayShop");
            return (Criteria) this;
        }

        public Criteria andActualPlayShopGreaterThanOrEqualTo(String value) {
            addCriterion("actual_play_shop >=", value, "actualPlayShop");
            return (Criteria) this;
        }

        public Criteria andActualPlayShopLessThan(String value) {
            addCriterion("actual_play_shop <", value, "actualPlayShop");
            return (Criteria) this;
        }

        public Criteria andActualPlayShopLessThanOrEqualTo(String value) {
            addCriterion("actual_play_shop <=", value, "actualPlayShop");
            return (Criteria) this;
        }

        public Criteria andActualPlayShopLike(String value) {
            addCriterion("actual_play_shop like", value, "actualPlayShop");
            return (Criteria) this;
        }

        public Criteria andActualPlayShopNotLike(String value) {
            addCriterion("actual_play_shop not like", value, "actualPlayShop");
            return (Criteria) this;
        }

        public Criteria andActualPlayShopIn(List<String> values) {
            addCriterion("actual_play_shop in", values, "actualPlayShop");
            return (Criteria) this;
        }

        public Criteria andActualPlayShopNotIn(List<String> values) {
            addCriterion("actual_play_shop not in", values, "actualPlayShop");
            return (Criteria) this;
        }

        public Criteria andActualPlayShopBetween(String value1, String value2) {
            addCriterion("actual_play_shop between", value1, value2, "actualPlayShop");
            return (Criteria) this;
        }

        public Criteria andActualPlayShopNotBetween(String value1, String value2) {
            addCriterion("actual_play_shop not between", value1, value2, "actualPlayShop");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(String value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(String value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(String value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(String value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(String value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(String value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLike(String value) {
            addCriterion("version like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotLike(String value) {
            addCriterion("version not like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<String> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<String> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(String value1, String value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(String value1, String value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameEngIsNull() {
            addCriterion("name_eng is null");
            return (Criteria) this;
        }

        public Criteria andNameEngIsNotNull() {
            addCriterion("name_eng is not null");
            return (Criteria) this;
        }

        public Criteria andNameEngEqualTo(String value) {
            addCriterion("name_eng =", value, "nameEng");
            return (Criteria) this;
        }

        public Criteria andNameEngNotEqualTo(String value) {
            addCriterion("name_eng <>", value, "nameEng");
            return (Criteria) this;
        }

        public Criteria andNameEngGreaterThan(String value) {
            addCriterion("name_eng >", value, "nameEng");
            return (Criteria) this;
        }

        public Criteria andNameEngGreaterThanOrEqualTo(String value) {
            addCriterion("name_eng >=", value, "nameEng");
            return (Criteria) this;
        }

        public Criteria andNameEngLessThan(String value) {
            addCriterion("name_eng <", value, "nameEng");
            return (Criteria) this;
        }

        public Criteria andNameEngLessThanOrEqualTo(String value) {
            addCriterion("name_eng <=", value, "nameEng");
            return (Criteria) this;
        }

        public Criteria andNameEngLike(String value) {
            addCriterion("name_eng like", value, "nameEng");
            return (Criteria) this;
        }

        public Criteria andNameEngNotLike(String value) {
            addCriterion("name_eng not like", value, "nameEng");
            return (Criteria) this;
        }

        public Criteria andNameEngIn(List<String> values) {
            addCriterion("name_eng in", values, "nameEng");
            return (Criteria) this;
        }

        public Criteria andNameEngNotIn(List<String> values) {
            addCriterion("name_eng not in", values, "nameEng");
            return (Criteria) this;
        }

        public Criteria andNameEngBetween(String value1, String value2) {
            addCriterion("name_eng between", value1, value2, "nameEng");
            return (Criteria) this;
        }

        public Criteria andNameEngNotBetween(String value1, String value2) {
            addCriterion("name_eng not between", value1, value2, "nameEng");
            return (Criteria) this;
        }

        public Criteria andNameNickIsNull() {
            addCriterion("name_nick is null");
            return (Criteria) this;
        }

        public Criteria andNameNickIsNotNull() {
            addCriterion("name_nick is not null");
            return (Criteria) this;
        }

        public Criteria andNameNickEqualTo(String value) {
            addCriterion("name_nick =", value, "nameNick");
            return (Criteria) this;
        }

        public Criteria andNameNickNotEqualTo(String value) {
            addCriterion("name_nick <>", value, "nameNick");
            return (Criteria) this;
        }

        public Criteria andNameNickGreaterThan(String value) {
            addCriterion("name_nick >", value, "nameNick");
            return (Criteria) this;
        }

        public Criteria andNameNickGreaterThanOrEqualTo(String value) {
            addCriterion("name_nick >=", value, "nameNick");
            return (Criteria) this;
        }

        public Criteria andNameNickLessThan(String value) {
            addCriterion("name_nick <", value, "nameNick");
            return (Criteria) this;
        }

        public Criteria andNameNickLessThanOrEqualTo(String value) {
            addCriterion("name_nick <=", value, "nameNick");
            return (Criteria) this;
        }

        public Criteria andNameNickLike(String value) {
            addCriterion("name_nick like", value, "nameNick");
            return (Criteria) this;
        }

        public Criteria andNameNickNotLike(String value) {
            addCriterion("name_nick not like", value, "nameNick");
            return (Criteria) this;
        }

        public Criteria andNameNickIn(List<String> values) {
            addCriterion("name_nick in", values, "nameNick");
            return (Criteria) this;
        }

        public Criteria andNameNickNotIn(List<String> values) {
            addCriterion("name_nick not in", values, "nameNick");
            return (Criteria) this;
        }

        public Criteria andNameNickBetween(String value1, String value2) {
            addCriterion("name_nick between", value1, value2, "nameNick");
            return (Criteria) this;
        }

        public Criteria andNameNickNotBetween(String value1, String value2) {
            addCriterion("name_nick not between", value1, value2, "nameNick");
            return (Criteria) this;
        }

        public Criteria andNameBakIsNull() {
            addCriterion("name_bak is null");
            return (Criteria) this;
        }

        public Criteria andNameBakIsNotNull() {
            addCriterion("name_bak is not null");
            return (Criteria) this;
        }

        public Criteria andNameBakEqualTo(String value) {
            addCriterion("name_bak =", value, "nameBak");
            return (Criteria) this;
        }

        public Criteria andNameBakNotEqualTo(String value) {
            addCriterion("name_bak <>", value, "nameBak");
            return (Criteria) this;
        }

        public Criteria andNameBakGreaterThan(String value) {
            addCriterion("name_bak >", value, "nameBak");
            return (Criteria) this;
        }

        public Criteria andNameBakGreaterThanOrEqualTo(String value) {
            addCriterion("name_bak >=", value, "nameBak");
            return (Criteria) this;
        }

        public Criteria andNameBakLessThan(String value) {
            addCriterion("name_bak <", value, "nameBak");
            return (Criteria) this;
        }

        public Criteria andNameBakLessThanOrEqualTo(String value) {
            addCriterion("name_bak <=", value, "nameBak");
            return (Criteria) this;
        }

        public Criteria andNameBakLike(String value) {
            addCriterion("name_bak like", value, "nameBak");
            return (Criteria) this;
        }

        public Criteria andNameBakNotLike(String value) {
            addCriterion("name_bak not like", value, "nameBak");
            return (Criteria) this;
        }

        public Criteria andNameBakIn(List<String> values) {
            addCriterion("name_bak in", values, "nameBak");
            return (Criteria) this;
        }

        public Criteria andNameBakNotIn(List<String> values) {
            addCriterion("name_bak not in", values, "nameBak");
            return (Criteria) this;
        }

        public Criteria andNameBakBetween(String value1, String value2) {
            addCriterion("name_bak between", value1, value2, "nameBak");
            return (Criteria) this;
        }

        public Criteria andNameBakNotBetween(String value1, String value2) {
            addCriterion("name_bak not between", value1, value2, "nameBak");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andTagIsNull() {
            addCriterion("tag is null");
            return (Criteria) this;
        }

        public Criteria andTagIsNotNull() {
            addCriterion("tag is not null");
            return (Criteria) this;
        }

        public Criteria andTagEqualTo(String value) {
            addCriterion("tag =", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotEqualTo(String value) {
            addCriterion("tag <>", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThan(String value) {
            addCriterion("tag >", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThanOrEqualTo(String value) {
            addCriterion("tag >=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThan(String value) {
            addCriterion("tag <", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThanOrEqualTo(String value) {
            addCriterion("tag <=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLike(String value) {
            addCriterion("tag like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotLike(String value) {
            addCriterion("tag not like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagIn(List<String> values) {
            addCriterion("tag in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotIn(List<String> values) {
            addCriterion("tag not in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagBetween(String value1, String value2) {
            addCriterion("tag between", value1, value2, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotBetween(String value1, String value2) {
            addCriterion("tag not between", value1, value2, "tag");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andLogoIsNull() {
            addCriterion("logo is null");
            return (Criteria) this;
        }

        public Criteria andLogoIsNotNull() {
            addCriterion("logo is not null");
            return (Criteria) this;
        }

        public Criteria andLogoEqualTo(String value) {
            addCriterion("logo =", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotEqualTo(String value) {
            addCriterion("logo <>", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThan(String value) {
            addCriterion("logo >", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoGreaterThanOrEqualTo(String value) {
            addCriterion("logo >=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThan(String value) {
            addCriterion("logo <", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLessThanOrEqualTo(String value) {
            addCriterion("logo <=", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoLike(String value) {
            addCriterion("logo like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotLike(String value) {
            addCriterion("logo not like", value, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoIn(List<String> values) {
            addCriterion("logo in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotIn(List<String> values) {
            addCriterion("logo not in", values, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoBetween(String value1, String value2) {
            addCriterion("logo between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andLogoNotBetween(String value1, String value2) {
            addCriterion("logo not between", value1, value2, "logo");
            return (Criteria) this;
        }

        public Criteria andImgIsNull() {
            addCriterion("img is null");
            return (Criteria) this;
        }

        public Criteria andImgIsNotNull() {
            addCriterion("img is not null");
            return (Criteria) this;
        }

        public Criteria andImgEqualTo(String value) {
            addCriterion("img =", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotEqualTo(String value) {
            addCriterion("img <>", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgGreaterThan(String value) {
            addCriterion("img >", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgGreaterThanOrEqualTo(String value) {
            addCriterion("img >=", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgLessThan(String value) {
            addCriterion("img <", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgLessThanOrEqualTo(String value) {
            addCriterion("img <=", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgLike(String value) {
            addCriterion("img like", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotLike(String value) {
            addCriterion("img not like", value, "img");
            return (Criteria) this;
        }

        public Criteria andImgIn(List<String> values) {
            addCriterion("img in", values, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotIn(List<String> values) {
            addCriterion("img not in", values, "img");
            return (Criteria) this;
        }

        public Criteria andImgBetween(String value1, String value2) {
            addCriterion("img between", value1, value2, "img");
            return (Criteria) this;
        }

        public Criteria andImgNotBetween(String value1, String value2) {
            addCriterion("img not between", value1, value2, "img");
            return (Criteria) this;
        }

        public Criteria andDeveloperIsNull() {
            addCriterion("developer is null");
            return (Criteria) this;
        }

        public Criteria andDeveloperIsNotNull() {
            addCriterion("developer is not null");
            return (Criteria) this;
        }

        public Criteria andDeveloperEqualTo(String value) {
            addCriterion("developer =", value, "developer");
            return (Criteria) this;
        }

        public Criteria andDeveloperNotEqualTo(String value) {
            addCriterion("developer <>", value, "developer");
            return (Criteria) this;
        }

        public Criteria andDeveloperGreaterThan(String value) {
            addCriterion("developer >", value, "developer");
            return (Criteria) this;
        }

        public Criteria andDeveloperGreaterThanOrEqualTo(String value) {
            addCriterion("developer >=", value, "developer");
            return (Criteria) this;
        }

        public Criteria andDeveloperLessThan(String value) {
            addCriterion("developer <", value, "developer");
            return (Criteria) this;
        }

        public Criteria andDeveloperLessThanOrEqualTo(String value) {
            addCriterion("developer <=", value, "developer");
            return (Criteria) this;
        }

        public Criteria andDeveloperLike(String value) {
            addCriterion("developer like", value, "developer");
            return (Criteria) this;
        }

        public Criteria andDeveloperNotLike(String value) {
            addCriterion("developer not like", value, "developer");
            return (Criteria) this;
        }

        public Criteria andDeveloperIn(List<String> values) {
            addCriterion("developer in", values, "developer");
            return (Criteria) this;
        }

        public Criteria andDeveloperNotIn(List<String> values) {
            addCriterion("developer not in", values, "developer");
            return (Criteria) this;
        }

        public Criteria andDeveloperBetween(String value1, String value2) {
            addCriterion("developer between", value1, value2, "developer");
            return (Criteria) this;
        }

        public Criteria andDeveloperNotBetween(String value1, String value2) {
            addCriterion("developer not between", value1, value2, "developer");
            return (Criteria) this;
        }

        public Criteria andPublisherIsNull() {
            addCriterion("publisher is null");
            return (Criteria) this;
        }

        public Criteria andPublisherIsNotNull() {
            addCriterion("publisher is not null");
            return (Criteria) this;
        }

        public Criteria andPublisherEqualTo(String value) {
            addCriterion("publisher =", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotEqualTo(String value) {
            addCriterion("publisher <>", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThan(String value) {
            addCriterion("publisher >", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThanOrEqualTo(String value) {
            addCriterion("publisher >=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThan(String value) {
            addCriterion("publisher <", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThanOrEqualTo(String value) {
            addCriterion("publisher <=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLike(String value) {
            addCriterion("publisher like", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotLike(String value) {
            addCriterion("publisher not like", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherIn(List<String> values) {
            addCriterion("publisher in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotIn(List<String> values) {
            addCriterion("publisher not in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherBetween(String value1, String value2) {
            addCriterion("publisher between", value1, value2, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotBetween(String value1, String value2) {
            addCriterion("publisher not between", value1, value2, "publisher");
            return (Criteria) this;
        }

        public Criteria andLowestPriceCurrencyIsNull() {
            addCriterion("lowest_price_currency is null");
            return (Criteria) this;
        }

        public Criteria andLowestPriceCurrencyIsNotNull() {
            addCriterion("lowest_price_currency is not null");
            return (Criteria) this;
        }

        public Criteria andLowestPriceCurrencyEqualTo(String value) {
            addCriterion("lowest_price_currency =", value, "lowestPriceCurrency");
            return (Criteria) this;
        }

        public Criteria andLowestPriceCurrencyNotEqualTo(String value) {
            addCriterion("lowest_price_currency <>", value, "lowestPriceCurrency");
            return (Criteria) this;
        }

        public Criteria andLowestPriceCurrencyGreaterThan(String value) {
            addCriterion("lowest_price_currency >", value, "lowestPriceCurrency");
            return (Criteria) this;
        }

        public Criteria andLowestPriceCurrencyGreaterThanOrEqualTo(String value) {
            addCriterion("lowest_price_currency >=", value, "lowestPriceCurrency");
            return (Criteria) this;
        }

        public Criteria andLowestPriceCurrencyLessThan(String value) {
            addCriterion("lowest_price_currency <", value, "lowestPriceCurrency");
            return (Criteria) this;
        }

        public Criteria andLowestPriceCurrencyLessThanOrEqualTo(String value) {
            addCriterion("lowest_price_currency <=", value, "lowestPriceCurrency");
            return (Criteria) this;
        }

        public Criteria andLowestPriceCurrencyLike(String value) {
            addCriterion("lowest_price_currency like", value, "lowestPriceCurrency");
            return (Criteria) this;
        }

        public Criteria andLowestPriceCurrencyNotLike(String value) {
            addCriterion("lowest_price_currency not like", value, "lowestPriceCurrency");
            return (Criteria) this;
        }

        public Criteria andLowestPriceCurrencyIn(List<String> values) {
            addCriterion("lowest_price_currency in", values, "lowestPriceCurrency");
            return (Criteria) this;
        }

        public Criteria andLowestPriceCurrencyNotIn(List<String> values) {
            addCriterion("lowest_price_currency not in", values, "lowestPriceCurrency");
            return (Criteria) this;
        }

        public Criteria andLowestPriceCurrencyBetween(String value1, String value2) {
            addCriterion("lowest_price_currency between", value1, value2, "lowestPriceCurrency");
            return (Criteria) this;
        }

        public Criteria andLowestPriceCurrencyNotBetween(String value1, String value2) {
            addCriterion("lowest_price_currency not between", value1, value2, "lowestPriceCurrency");
            return (Criteria) this;
        }

        public Criteria andLowestPriceIsNull() {
            addCriterion("lowest_price is null");
            return (Criteria) this;
        }

        public Criteria andLowestPriceIsNotNull() {
            addCriterion("lowest_price is not null");
            return (Criteria) this;
        }

        public Criteria andLowestPriceEqualTo(BigDecimal value) {
            addCriterion("lowest_price =", value, "lowestPrice");
            return (Criteria) this;
        }

        public Criteria andLowestPriceNotEqualTo(BigDecimal value) {
            addCriterion("lowest_price <>", value, "lowestPrice");
            return (Criteria) this;
        }

        public Criteria andLowestPriceGreaterThan(BigDecimal value) {
            addCriterion("lowest_price >", value, "lowestPrice");
            return (Criteria) this;
        }

        public Criteria andLowestPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("lowest_price >=", value, "lowestPrice");
            return (Criteria) this;
        }

        public Criteria andLowestPriceLessThan(BigDecimal value) {
            addCriterion("lowest_price <", value, "lowestPrice");
            return (Criteria) this;
        }

        public Criteria andLowestPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("lowest_price <=", value, "lowestPrice");
            return (Criteria) this;
        }

        public Criteria andLowestPriceIn(List<BigDecimal> values) {
            addCriterion("lowest_price in", values, "lowestPrice");
            return (Criteria) this;
        }

        public Criteria andLowestPriceNotIn(List<BigDecimal> values) {
            addCriterion("lowest_price not in", values, "lowestPrice");
            return (Criteria) this;
        }

        public Criteria andLowestPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lowest_price between", value1, value2, "lowestPrice");
            return (Criteria) this;
        }

        public Criteria andLowestPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lowest_price not between", value1, value2, "lowestPrice");
            return (Criteria) this;
        }

        public Criteria andLowestPriceRmbIsNull() {
            addCriterion("lowest_price_rmb is null");
            return (Criteria) this;
        }

        public Criteria andLowestPriceRmbIsNotNull() {
            addCriterion("lowest_price_rmb is not null");
            return (Criteria) this;
        }

        public Criteria andLowestPriceRmbEqualTo(BigDecimal value) {
            addCriterion("lowest_price_rmb =", value, "lowestPriceRmb");
            return (Criteria) this;
        }

        public Criteria andLowestPriceRmbNotEqualTo(BigDecimal value) {
            addCriterion("lowest_price_rmb <>", value, "lowestPriceRmb");
            return (Criteria) this;
        }

        public Criteria andLowestPriceRmbGreaterThan(BigDecimal value) {
            addCriterion("lowest_price_rmb >", value, "lowestPriceRmb");
            return (Criteria) this;
        }

        public Criteria andLowestPriceRmbGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("lowest_price_rmb >=", value, "lowestPriceRmb");
            return (Criteria) this;
        }

        public Criteria andLowestPriceRmbLessThan(BigDecimal value) {
            addCriterion("lowest_price_rmb <", value, "lowestPriceRmb");
            return (Criteria) this;
        }

        public Criteria andLowestPriceRmbLessThanOrEqualTo(BigDecimal value) {
            addCriterion("lowest_price_rmb <=", value, "lowestPriceRmb");
            return (Criteria) this;
        }

        public Criteria andLowestPriceRmbIn(List<BigDecimal> values) {
            addCriterion("lowest_price_rmb in", values, "lowestPriceRmb");
            return (Criteria) this;
        }

        public Criteria andLowestPriceRmbNotIn(List<BigDecimal> values) {
            addCriterion("lowest_price_rmb not in", values, "lowestPriceRmb");
            return (Criteria) this;
        }

        public Criteria andLowestPriceRmbBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lowest_price_rmb between", value1, value2, "lowestPriceRmb");
            return (Criteria) this;
        }

        public Criteria andLowestPriceRmbNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lowest_price_rmb not between", value1, value2, "lowestPriceRmb");
            return (Criteria) this;
        }

        public Criteria andPurchaseDateIsNull() {
            addCriterion("purchase_date is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseDateIsNotNull() {
            addCriterion("purchase_date is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseDateEqualTo(Date value) {
            addCriterionForJDBCDate("purchase_date =", value, "purchaseDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("purchase_date <>", value, "purchaseDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDateGreaterThan(Date value) {
            addCriterionForJDBCDate("purchase_date >", value, "purchaseDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("purchase_date >=", value, "purchaseDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDateLessThan(Date value) {
            addCriterionForJDBCDate("purchase_date <", value, "purchaseDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("purchase_date <=", value, "purchaseDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDateIn(List<Date> values) {
            addCriterionForJDBCDate("purchase_date in", values, "purchaseDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("purchase_date not in", values, "purchaseDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("purchase_date between", value1, value2, "purchaseDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("purchase_date not between", value1, value2, "purchaseDate");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceCurrencyIsNull() {
            addCriterion("purchase_price_currency is null");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceCurrencyIsNotNull() {
            addCriterion("purchase_price_currency is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceCurrencyEqualTo(String value) {
            addCriterion("purchase_price_currency =", value, "purchasePriceCurrency");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceCurrencyNotEqualTo(String value) {
            addCriterion("purchase_price_currency <>", value, "purchasePriceCurrency");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceCurrencyGreaterThan(String value) {
            addCriterion("purchase_price_currency >", value, "purchasePriceCurrency");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceCurrencyGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_price_currency >=", value, "purchasePriceCurrency");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceCurrencyLessThan(String value) {
            addCriterion("purchase_price_currency <", value, "purchasePriceCurrency");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceCurrencyLessThanOrEqualTo(String value) {
            addCriterion("purchase_price_currency <=", value, "purchasePriceCurrency");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceCurrencyLike(String value) {
            addCriterion("purchase_price_currency like", value, "purchasePriceCurrency");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceCurrencyNotLike(String value) {
            addCriterion("purchase_price_currency not like", value, "purchasePriceCurrency");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceCurrencyIn(List<String> values) {
            addCriterion("purchase_price_currency in", values, "purchasePriceCurrency");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceCurrencyNotIn(List<String> values) {
            addCriterion("purchase_price_currency not in", values, "purchasePriceCurrency");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceCurrencyBetween(String value1, String value2) {
            addCriterion("purchase_price_currency between", value1, value2, "purchasePriceCurrency");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceCurrencyNotBetween(String value1, String value2) {
            addCriterion("purchase_price_currency not between", value1, value2, "purchasePriceCurrency");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceIsNull() {
            addCriterion("purchase_price is null");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceIsNotNull() {
            addCriterion("purchase_price is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceEqualTo(BigDecimal value) {
            addCriterion("purchase_price =", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceNotEqualTo(BigDecimal value) {
            addCriterion("purchase_price <>", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceGreaterThan(BigDecimal value) {
            addCriterion("purchase_price >", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("purchase_price >=", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceLessThan(BigDecimal value) {
            addCriterion("purchase_price <", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("purchase_price <=", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceIn(List<BigDecimal> values) {
            addCriterion("purchase_price in", values, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceNotIn(List<BigDecimal> values) {
            addCriterion("purchase_price not in", values, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchase_price between", value1, value2, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchase_price not between", value1, value2, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceRmbIsNull() {
            addCriterion("purchase_price_rmb is null");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceRmbIsNotNull() {
            addCriterion("purchase_price_rmb is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceRmbEqualTo(BigDecimal value) {
            addCriterion("purchase_price_rmb =", value, "purchasePriceRmb");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceRmbNotEqualTo(BigDecimal value) {
            addCriterion("purchase_price_rmb <>", value, "purchasePriceRmb");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceRmbGreaterThan(BigDecimal value) {
            addCriterion("purchase_price_rmb >", value, "purchasePriceRmb");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceRmbGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("purchase_price_rmb >=", value, "purchasePriceRmb");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceRmbLessThan(BigDecimal value) {
            addCriterion("purchase_price_rmb <", value, "purchasePriceRmb");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceRmbLessThanOrEqualTo(BigDecimal value) {
            addCriterion("purchase_price_rmb <=", value, "purchasePriceRmb");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceRmbIn(List<BigDecimal> values) {
            addCriterion("purchase_price_rmb in", values, "purchasePriceRmb");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceRmbNotIn(List<BigDecimal> values) {
            addCriterion("purchase_price_rmb not in", values, "purchasePriceRmb");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceRmbBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchase_price_rmb between", value1, value2, "purchasePriceRmb");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceRmbNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchase_price_rmb not between", value1, value2, "purchasePriceRmb");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("note not between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("create_by like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("create_by not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(String value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(String value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(String value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(String value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(String value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(String value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLike(String value) {
            addCriterion("update_by like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotLike(String value) {
            addCriterion("update_by not like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<String> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<String> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(String value1, String value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(String value1, String value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}