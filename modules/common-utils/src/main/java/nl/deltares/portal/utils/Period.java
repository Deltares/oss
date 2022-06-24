package nl.deltares.portal.utils;

import java.util.*;

public class Period implements Comparable<Period>{

    public static final Period NEVER = new Period(Long.MAX_VALUE, Long.MIN_VALUE, true);
    public static final Period ANY_TIME = new Period(Long.MIN_VALUE, Long.MAX_VALUE, true);
    public static final Period MIN_VALUE = new Period(Long.MIN_VALUE, Long.MIN_VALUE, true);
    public static final Period MAX_VALUE = new Period(Long.MAX_VALUE, Long.MAX_VALUE, true);

    private final long startTime;
    private final long endTime;

    /**
     * @see #NEVER
     * @see #ANY_TIME
     */
    private Period(long startTime, long endTime, boolean privateConstructor) {
        assert privateConstructor;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Period(long startTime, long endTime) {
        if (startTime == Long.MAX_VALUE) {
            throw new IllegalArgumentException("startTime == Long.MAX_VALUE, endTime == " + new Date(endTime));
        }

        if (endTime == Long.MIN_VALUE) {
            throw new IllegalArgumentException("endTime == Long.MIN_VALUE, startTime == " + new Date(startTime));
        }

        if (startTime == Long.MIN_VALUE && endTime == Long.MAX_VALUE)
            throw new IllegalArgumentException("startTime == Long.MIN_VALUE && endTime == Long.MAX_VALUE, use Period.ANY_TIME or Period.create instead");

        if (endTime < startTime) {
            throw new IllegalArgumentException("endTime can not be before startTime: "
                    + new Date(startTime) + ", " + new Date(endTime));
        }

        if (startTime != Long.MIN_VALUE && endTime != Long.MAX_VALUE && endTime - startTime < 0L)
            throw new IllegalArgumentException("Overflow for times " + new Date(startTime) + ' ' + new Date(endTime));

        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Period(Date startDate, Date endDate) {
        this(startDate.getTime(), endDate.getTime());
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public Date getStartDate() {
        return new Date(startTime);
    }

    public Date getEndDate() {
        return new Date(endTime);
    }

    @Override
    public String toString() {
        return new Date(startTime) + " to " + new Date(endTime);
    }

    public long getDuration() {
        if (this == NEVER) return Long.MIN_VALUE;
        long startTime = this.startTime;
        long endTime = this.endTime;
        if (startTime == Long.MIN_VALUE) return Long.MAX_VALUE;
        if (endTime == Long.MAX_VALUE) return Long.MAX_VALUE;
        assert startTime <= endTime;
        long res = endTime - startTime;
        if (res < 0) return Long.MAX_VALUE; // overflow
        return res;
    }

    public boolean containsSingleTime() {
        return startTime == endTime;
    }

    public long getDuration(long timeUnit) {
        if (this == NEVER) return Long.MIN_VALUE;
        if (startTime == Long.MIN_VALUE) return Long.MAX_VALUE;
        if (endTime == Long.MAX_VALUE) return Long.MAX_VALUE;
        return (endTime - startTime) / timeUnit;
    }

    /**
     * Check if argument is contained by this period.
     * Both start and end times are included ( >= startTime and <= endTime)
     * @param time
     * @return
     */
    public boolean contains(long time) {
        return startTime <= time && time <= endTime;
    }

    public boolean contains(Date date) {
        return contains(date.getTime());
    }

    public boolean contains(Period period) {
        if (period == null)
            throw new IllegalArgumentException(" period == null" ) ;
        if (period == NEVER) return false;
        return startTime <= period.startTime && period.endTime <= endTime;
    }

    public boolean contains(long startTime, long endTime) {
        return this.startTime <= startTime && endTime <= this.endTime;
    }

    public boolean containsAny(Collection<Long> times) {
        for (long time : times) {
            if (contains(time)) return true;
        }
        return false;
    }

    public Period join(Period period) {
        if (period == null)
            throw new IllegalArgumentException("period == null");

        if (period == NEVER) return this;
        if (this == NEVER) return period;
        return create(Math.min(startTime, period.startTime),
                Math.max(endTime, period.endTime));
    }

    public Period join(long startTime, long endTime) {
        return new Period(Math.min(startTime, this.startTime),
                Math.max(endTime, this.endTime));
    }

    public Period getCommon(Period period) {
        if (period == null)
            throw new IllegalArgumentException("period == null");

        if (period == NEVER) return NEVER;
        if (period == ANY_TIME) return this;

        long start = Math.max(startTime, period.startTime);
        long end = Math.min(endTime, period.endTime);

        if (start > end) return NEVER;
        if (startTime == start && endTime == end) return this;
        return new Period(start, end);
    }

    public Period getCommon(long startTime, long endTime) {
        if (startTime == Long.MAX_VALUE)
            throw new IllegalArgumentException("startTime == Long.MAX_VALUE");

        if (endTime == Long.MIN_VALUE)
            throw new IllegalArgumentException("endTime == Long.MIN_VALUE");

        if (startTime > endTime)
            throw new IllegalArgumentException("startTime > endTime");

        long start = Math.max(this.startTime, startTime);
        long end = Math.min(this.endTime, endTime);

        if (start > end) return NEVER;
        if (this.startTime == start && this.endTime == end) return this;
        return create(start, end);
    }

    /**
     * If there is a gap between this period and the given period, then
     * this method returns the gapPeriod. Otherwise returns Period.NEVER.
     *
     * @param period
     * @return gapPeriod.
     */
    public Period getGap(Period period) {
        if (period == null)
            throw new IllegalArgumentException("period == null");

        if (this == NEVER || period == NEVER) return NEVER;
        if (this == ANY_TIME || period == ANY_TIME) return NEVER;
        Period first = endTime < period.startTime ? this : period;
        Period last = startTime > period.endTime ? this : period;
        long start = first.endTime;
        long end = last.startTime;
        if (start >= end) return NEVER;
        return new Period(start, end);
    }

    public Period extendTo(Date date) {
        return extendTo(date.getTime());
    }

    public Period extendTo(long aTime) {
        long newStartTime = aTime < startTime ? aTime : startTime;
        long newEndTime = aTime > endTime ? aTime : endTime;

        return create(newStartTime, newEndTime);
    }

    public boolean isStartedAt(long aTime) {
        return startTime == aTime;
    }

    public boolean isStartedAt(Date aDate) {
        return startTime == aDate.getTime();
    }

    public boolean isEndedAt(long aTime) {
        return endTime == aTime;
    }

    public boolean isEndedAt(Date aDate) {
        return endTime == aDate.getTime();
    }

    public boolean isAnyTimeCommon(Period period, boolean allowSameBoundaries) {
        if (this == NEVER) return false;
        if (this == ANY_TIME) return true;
        if ( allowSameBoundaries ) return startTime < period.endTime && period.startTime < endTime;
        return startTime <= period.endTime && period.startTime <= endTime;
    }

    public boolean isAnyTimeCommon(long periodStartTime, long periodEndTime) {
        if (this == NEVER) return false;
        if (this == ANY_TIME) return true;
        return startTime <= periodEndTime && periodStartTime <= endTime;
    }

    public boolean equals(long startTime, long endTime) {
        if (this.endTime != endTime) return false;
        return this.startTime == startTime;
    }

    public boolean equals(Period period) {
        if (this == period) return true;
        if (period == null) return false;
        if (endTime != period.endTime) return false;
        return startTime == period.startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (hashCode() != o.hashCode()) return false;
        if (Period.class != o.getClass()) return false;

        Period period = (Period) o;

        if (endTime != period.endTime) return false;
        return startTime == period.startTime;
    }

    @Override
    public int hashCode() {
        return  (int) (startTime ^ startTime >>> 32) + 31 * (int) (endTime ^ endTime >>> 32);
    }

    public boolean hasStart() {
        if (this == NEVER) return false;
        return startTime != Long.MIN_VALUE;
    }

    public boolean hasEnd() {
        if (this == NEVER) return false;
        return endTime != Long.MAX_VALUE;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.<p>
     * @param o the Object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     * @throws ClassCastException if the specified object's type prevents it
     *                            from being compared to this Object.
     */
    @Override
    public int compareTo(Period o) {
        if (o == null)
            throw new IllegalArgumentException("o == null");

        if (startTime < o.startTime) return -1;
        if (startTime > o.startTime) return 1;
        // if the start times are equal then compare the end times.
        if (endTime < o.endTime) return -1;
        if (endTime > o.endTime) return 1;
        return 0;
    }

    public static Period create(long time) {
        return create(time, time);
    }

    private static Period lastPeriod = NEVER;

    public static Period create(long startTime, long endTime) {
        if (startTime > endTime) return NEVER;
        if (startTime == Long.MAX_VALUE && endTime == Long.MIN_VALUE) return NEVER;
        if (startTime == Long.MIN_VALUE && endTime == Long.MAX_VALUE) return ANY_TIME;
        if (startTime == Long.MIN_VALUE && endTime == Long.MIN_VALUE) return MIN_VALUE;
        if (startTime == Long.MAX_VALUE && endTime == Long.MAX_VALUE) return MAX_VALUE;
        Period res = lastPeriod;
        if (res.equals(startTime, endTime)) return res;
        res = new Period(startTime, endTime);
        lastPeriod = res;
        return res;
    }

    public static Period join(List<Period> periods) {
        if (periods.isEmpty()) return NEVER;
        Period firstPeriod = periods.get(0);
        if (periods.size() == 1) return firstPeriod;
        long start = firstPeriod.startTime;
        long end = firstPeriod.endTime;
        for (int i = 1, n = periods.size(); i < n; i++) {
            Period period = periods.get(i);
            if (period.startTime < start) start = period.startTime;
            if (period.endTime > end) end = period.endTime;
        }
        if (start == Long.MAX_VALUE) return NEVER;
        if (firstPeriod.equals(start, end)) return firstPeriod;
        return create(start, end);
    }

    public static Period join(Period[] periods) {
        if (periods.length == 0) return NEVER;
        Period firstPeriod = periods[0];
        if (periods.length == 1) return firstPeriod;
        long start = firstPeriod.startTime;
        long end = firstPeriod.endTime;
        for (int i = 1; i < periods.length; i++) {
            Period period = periods[i];
            if (period.startTime < start) start = period.startTime;
            if (period.endTime > end) end = period.endTime;
        }
        if (start == Long.MAX_VALUE) return NEVER;
        if (firstPeriod.equals(start, end)) return firstPeriod;
        return create(start, end);
    }

    private static String formatTime(int hour, int minute, int seconds, int millis) {
        StringBuilder res = new StringBuilder(10);
        res.append(hour);
        res.append(':');
        if (minute < 10) res.append('0');
        res.append(minute);
        if (seconds == 0 && millis == 0) return res.toString();
        res.append(':');
        if (seconds < 10) res.append('0');
        res.append(seconds);
        if (millis == 0) return res.toString();
        res.append('.');
        if (millis < 100) res.append('0');
        if (millis < 10) res.append('0');
        res.append(millis);
        return res.toString();
    }

    public static boolean equals(Period a, Period b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        return a.startTime == b.startTime && a.endTime == b.endTime;
    }

    public static boolean containsOverlap(Period[] sorted) {
        Period previous = sorted[0];
        for (int i = 1; i < sorted.length; i++) {
            Period period = sorted[i];
            if (period.isAnyTimeCommon(previous, true)) return true;
            previous  = period;
        }

        return false;
    }

}
