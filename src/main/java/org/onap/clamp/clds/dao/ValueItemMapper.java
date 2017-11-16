/*-
 * ============LICENSE_START=======================================================
 * ONAP CLAMP
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights
 *                             reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END============================================
 * ===================================================================
 * ECOMP is a trademark and service mark of AT&T Intellectual Property.
 */

package org.onap.clamp.clds.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.onap.clamp.clds.model.ValueItem;
import org.springframework.jdbc.core.RowMapper;

/**
 * Generic mapper for list of values
 */
public final class ValueItemMapper implements RowMapper<ValueItem> {
    @Override
    public ValueItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        ValueItem item = new ValueItem();
        item.setValue(rs.getString(1));
        return item;
    }
}