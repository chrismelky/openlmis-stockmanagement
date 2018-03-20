/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.stockmanagement.service.referencedata;

import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.openlmis.stockmanagement.dto.referencedata.ApprovedProductDto;
import org.openlmis.stockmanagement.dto.referencedata.OrderableDto;
import org.openlmis.stockmanagement.service.BaseCommunicationService;
import org.openlmis.stockmanagement.testutils.ApprovedProductDtoDataBuilder;
import org.openlmis.stockmanagement.util.RequestParameters;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.UUID;

public class ApprovedProductReferenceDataServiceTest
    extends BaseReferenceDataServiceTest<ApprovedProductDto> {

  private ApprovedProductReferenceDataService service;

  @Override
  protected BaseCommunicationService<ApprovedProductDto> getService() {
    return new ApprovedProductReferenceDataService();
  }

  @Override
  protected ApprovedProductDto generateInstance() {
    return new ApprovedProductDtoDataBuilder().build();
  }

  @Before
  public void setUp() throws Exception {
    super.setUp();
    service = (ApprovedProductReferenceDataService) prepareService();
  }

  @Test
  public void shouldReturnMapOfOrderableFulfills() {
    UUID programId = randomUUID();
    Collection<UUID> orderableIds = asList(randomUUID(), randomUUID());

    RequestParameters parameters = RequestParameters.init();
    parameters.set("programId", programId);
    parameters.set("orderableId", orderableIds);

    ApprovedProductDto approvedProduct = generateInstance();
    UUID facilityId = randomUUID();

    mockPageResponseEntity(approvedProduct);

    Page<OrderableDto> result = service
        .getApprovedProducts(facilityId, programId, orderableIds);

    assertEquals(1, result.getTotalElements());
    assertEquals(approvedProduct.getOrderable(), result.getContent().get(0));
  }
}
