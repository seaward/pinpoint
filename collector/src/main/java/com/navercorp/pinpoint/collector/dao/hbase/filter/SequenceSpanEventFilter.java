/*
 * *
 *  * Copyright 2016 NAVER Corp.
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.navercorp.pinpoint.collector.dao.hbase.filter;

import com.navercorp.pinpoint.common.bo.SpanEventBo;
import com.navercorp.pinpoint.common.util.BytesUtils;


/**
 * @author Woonduk Kang(emeroad)
 */
public class SequenceSpanEventFilter implements SpanEventFilter {

    public static final int MAX_SEQUENCE = Short.MAX_VALUE;
    public static final int DEFAULT_SEQUENCE_LIMIT = 1024*10;

    private final int sequenceLimit;

    public SequenceSpanEventFilter() {
        this(DEFAULT_SEQUENCE_LIMIT);
    }

    public SequenceSpanEventFilter(int sequenceLimit) {
        if (sequenceLimit > MAX_SEQUENCE) {
            throw new IllegalArgumentException(sequenceLimit + " > MAX_SEQUENCE");
        }
        this.sequenceLimit = sequenceLimit;
    }

    @Override
    public boolean filter(SpanEventBo spanEventBo) {
        if (spanEventBo == null) {
            return REJECT;
        }
        final int sequence = spanEventBo.getSequence();
        if (sequence > sequenceLimit) {
            return REJECT;
        }
        return ACCEPT;
    }
}
