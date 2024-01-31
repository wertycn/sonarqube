/*
 * SonarQube
 * Copyright (C) 2009-2024 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
import { MetricsLabel, MetricsRatingBadge, NumericalCell } from 'design-system';
import * as React from 'react';
import Measure from '../../../components/measure/Measure';
import { translate, translateWithParameters } from '../../../helpers/l10n';
import { formatMeasure, isDiffMetric } from '../../../helpers/measures';
import { MetricType } from '../../../types/metrics';
import { ComponentMeasureEnhanced, MeasureEnhanced, Metric } from '../../../types/types';

interface Props {
  component: ComponentMeasureEnhanced;
  measure?: MeasureEnhanced;
  metric: Metric;
}

export default function MeasureCell({ component, measure, metric }: Props) {
  const getValue = (item: { leak?: string; value?: string }) =>
    isDiffMetric(metric.key) ? item.leak : item.value;

  const value = getValue(measure || component);

  return (
    <NumericalCell className="sw-py-3">
      <Measure
        metricKey={metric.key}
        metricType={metric.type}
        value={value}
        small
        ratingComponent={
          <MetricsRatingBadge
            label={
              value
                ? translateWithParameters(
                    'metric.has_rating_X',
                    formatMeasure(value, MetricType.Rating),
                  )
                : translate('metric.no_rating')
            }
            rating={formatMeasure(value, MetricType.Rating) as MetricsLabel}
          />
        }
      />
    </NumericalCell>
  );
}
