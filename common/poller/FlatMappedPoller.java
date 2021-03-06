/*
 * Copyright (C) 2021 Vaticle
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.vaticle.typedb.core.common.poller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FlatMappedPoller<T, U> extends AbstractPoller<U> {

    private final Poller<T> source;
    private final Function<T, Poller<U>> flatMappingFn;
    private final List<Poller<U>> flatMappedPollers;

    FlatMappedPoller(Poller<T> poller, Function<T, Poller<U>> flatMappingFn) {
        this.source = poller;
        this.flatMappingFn = flatMappingFn;
        this.flatMappedPollers = new ArrayList<>();
    }

    @Override
    public Optional<U> poll() {
        for (Poller<U> poller : flatMappedPollers) {
            Optional<U> next = poller.poll();
            if (next.isPresent()) return next;
        }
        Optional<T> fromSource;
        while ((fromSource = source.poll()).isPresent()) {
            Poller<U> newPoller = flatMappingFn.apply(fromSource.get());
            flatMappedPollers.add(newPoller);
            Optional<U> next = newPoller.poll();
            if (next.isPresent()) return next;
        }
        return Optional.empty();
    }

    @Override
    public void recycle() {
        flatMappedPollers.forEach(Poller::recycle);
        source.recycle();
    }

}
